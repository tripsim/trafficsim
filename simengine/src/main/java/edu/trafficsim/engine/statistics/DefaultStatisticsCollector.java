/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.engine.statistics;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.simulation.Tracker;
import edu.trafficsim.model.Vehicle;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("default-statistics-collector")
class DefaultStatisticsCollector implements StatisticsCollector {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultStatisticsCollector.class);

	@Autowired
	StatisticsCommittor committor;

	private Map<Tracker, Snapshot> map = new WeakHashMap<Tracker, Snapshot>();

	@Override
	public void visit(Tracker tracker, Vehicle vehicle) {
		if (vehicle == null || !vehicle.active() || vehicle.position() < 0) {
			return;
		}

		Snapshot snapshot = map.get(tracker);
		if (snapshot == null) {
			snapshot = newSnapshot(tracker);
		} else if (snapshot.sequence != tracker.getForwardedSteps()) {
			try {
				committor.commit(snapshot);
			} catch (InterruptedException e) {
				logger.info(
						"commiting staticstics from vehicle '{}' at step '{}' is interrupted",
						vehicle, snapshot.sequence);
			}
			snapshot = newSnapshot(tracker);
		}
		snapshot.visitVehicle(vehicle);
		if (vehicle.getStartFrame() == tracker.getForwardedSteps()) {
			snapshot.addNewVehicle(vehicle);
		}
	}

	private Snapshot newSnapshot(Tracker tracker) {
		Snapshot snapshot = new Snapshot(
				tracker.getSimulationName(), tracker.getForwardedSteps(),
				tracker.getForwardedTime());
		map.put(tracker, snapshot);
		return snapshot;
	}

}
