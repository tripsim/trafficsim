package edu.trafficsim.engine.statistics;

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

	ExecutorService executor;
	BlockingQueue<StatisticsSnapshot> snapshots = new LinkedBlockingDeque<StatisticsSnapshot>();

	public void commit(StatisticsSnapshot snapshot) throws InterruptedException {
		snapshots.put(snapshot);
	}

	class DbCommittor implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					StatisticsSnapshot snapshot = snapshots.take();
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
		} finally {
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
