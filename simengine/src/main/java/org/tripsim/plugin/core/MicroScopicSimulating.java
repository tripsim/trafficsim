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
package org.tripsim.plugin.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;
import org.tripsim.engine.simulation.SimulationEnvironment;
import org.tripsim.engine.statistics.StatisticsCollector;
import org.tripsim.engine.vehicle.VehicleFactory;
import org.tripsim.plugin.api.ISimulating;
import org.tripsim.util.Timer;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Micro Scopic Simulating")
public class MicroScopicSimulating extends AbstractMicroScopicSimulating
		implements ISimulating {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(MicroScopicSimulating.class);

	@Autowired
	VehicleFactory vehicleFactory;
	@Autowired
	StatisticsCollector statisticsCollector;

	@Override
	protected void beforeSimulate(Timer timer, SimulationEnvironment environ) {
		logger.info("******** Micro Scopic Simulation ********");
		logger.info("---- Parameters ----");
		logger.info("Random Seed: {} ", environ.getSeed());
		logger.info("Step Size: {}", environ.getStepSize());
		logger.info("Duration: {}", environ.getDuration());
		logger.info("---- Simulation ----");
	}

	@Override
	protected void moveVehicle(SimulationEnvironment environment,
			Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
		environment.applyCarFollowing(vehicle, stream, web);
		environment.applyLaneChanging(vehicle, stream, web);
		environment.applyMoving(vehicle, stream, web);
	}

	@Override
	protected void collectionStatistics(SimulationEnvironment environment,
			Vehicle vehicle) {
		logger.info("Time: {}s: {}", environment.getForwardedTime(), vehicle);
		statisticsCollector.visit(environment, vehicle);
	}

	@Override
	protected void afterGenerateVehicles(SimulationEnvironment environment,
			Od od, List<Vehicle> newVehicles) {
		logger.info(
				"Time: {}s: {} new vehicles generated at node {} to node {}",
				environment.getForwardedTime(), newVehicles.size(),
				od.getOriginNodeId(), od.getDestinationNodeId());
	}

	@Override
	public String getName() {
		return "Microscopic Simulating";
	}

}
