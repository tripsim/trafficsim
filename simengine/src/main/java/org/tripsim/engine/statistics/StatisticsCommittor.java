/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.engine.statistics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

@Service("default-statistics-committor")
class StatisticsCommittor implements SmartLifecycle {
	private static final Logger logger = LoggerFactory
			.getLogger(StatisticsCommittor.class);

	@Autowired
	StatisticsManager statisticsManager;

	private Lock lock = new ReentrantLock();
	private boolean started = false;

	private long shutdownWaitTime = 10000;
	ExecutorService executor;
	BlockingQueue<Snapshot> snapshots = new LinkedBlockingDeque<Snapshot>();

	public void commit(Snapshot snapshot) throws InterruptedException {
		snapshots.put(snapshot);
	}

	class DbCommittor implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					Snapshot snapshot = snapshots.take();
					statisticsManager.insertSnapshot(snapshot);
				}
			} catch (InterruptedException e) {
				logger.info("thread committing simualtion results is interrupted");
			}
		}

	}

	@Override
	public void start() {
		lock.lock();
		try {
			if (started) {
				return;
			}
			executor = Executors.newSingleThreadExecutor();
			executor.execute(new DbCommittor());
			started = true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void stop() {
		lock.lock();
		try {
			if (!started) {
				return;
			}
			logger.info("stopping satatistics committor thread!");

			executor.shutdown();
			if (snapshots.size() > 0) {
				long endTime = System.currentTimeMillis() + shutdownWaitTime;
				while (snapshots.size() > 0) {
					try {
						if (System.currentTimeMillis() > endTime) {
							executor.shutdownNow();
							logger.warn(
									"statistics committor cannot complete during shutdown waittime, {} snapshots not saved!",
									snapshots.size());
							break;
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.warn(
								"statistics committor intrupted during shutdown, {} snapshots not saved!",
								snapshots.size());
						break;
					} finally {
						executor.shutdownNow();
					}
				}
			}

		} finally {
			started = false;
			lock.unlock();
		}
	}

	@Override
	public boolean isRunning() {
		return started;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		if (callback != null) {
			callback.run();
		}
	}

}
