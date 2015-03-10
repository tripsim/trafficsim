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
package org.tripsim.engine.statistics;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.Vehicle;
import org.tripsim.engine.simulation.SimulationEnvironment;

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

	private Map<SimulationEnvironment, Snapshot> map = new WeakHashMap<SimulationEnvironment, Snapshot>();

	@Override
	public void visit(SimulationEnvironment environment, Vehicle vehicle) {
		if (vehicle == null || !vehicle.isActive() || vehicle.getPosition() < 0) {
			return;
		}
		
		Snapshot snapshot = map.get(environment);
		if (snapshot == null) {
			snapshot = newSnapshot(environment);
		} else if (snapshot.sequence != environment.getForwardedSteps()) {
			try {
				committor.commit(snapshot);
			} catch (InterruptedException e) {
				logger.info(
						"commiting staticstics from vehicle '{}' at step '{}' is interrupted",
						vehicle, snapshot.sequence);
			}
			snapshot = newSnapshot(environment);
		}
		snapshot.visitVehicle(vehicle);
		if (vehicle.getStartFrame() == environment.getForwardedSteps()) {
			snapshot.addNewVehicle(vehicle);
		}
	}

	private Snapshot newSnapshot(SimulationEnvironment environment) {
		Snapshot snapshot = new Snapshot(environment.getSimulationName(),
				environment.getForwardedSteps(), environment.getForwardedTime());
		map.put(environment, snapshot);
		return snapshot;
	}

}
