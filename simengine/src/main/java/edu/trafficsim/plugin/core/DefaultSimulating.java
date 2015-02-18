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
package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.engine.simulation.Tracker;
import edu.trafficsim.engine.statistics.StatisticsCollector;
import edu.trafficsim.engine.vehicle.VehicleFactory;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.ICarFollowing;
import edu.trafficsim.plugin.IMoving;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.IVehicleGenerating;
import edu.trafficsim.plugin.PluginManager;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Microscopic Simulating")
public class DefaultSimulating extends AbstractPlugin implements ISimulating {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSimulating.class);

	@Autowired
	VehicleFactory vehicleFactory;
	@Autowired
	StatisticsCollector statisticsCollector;

	@Override
	public void simulate(String outcomeName, Network network,
			OdMatrix odMatrix, SimulationSettings settings) {

		IMoving moving = pluginManager
				.getMovingImpl(PluginManager.DEFAULT_MOVING);
		ICarFollowing carFollowing = pluginManager
				.getCarFollowingImpl(PluginManager.DEFAULT_CAR_FOLLOWING);
		IVehicleGenerating vehicleGenerating = pluginManager
				.getVehicleGeneratingImpl(PluginManager.DEFAULT_GENERATING);

		Tracker tracker = new Tracker(outcomeName, settings);
		logger.info("******** Micro Scopic Simulation ********");
		logger.info("---- Parameters ----");
		logger.info("Random Seed: {} ", tracker.getSeed());
		logger.info("Step Size: {}", tracker.getStepSize());
		logger.info("Duration: {}", tracker.getDuration());

		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		logger.info("---- Simulation ----");
		while (!tracker.isFinished()) {
			double time = tracker.getForwardedTime();
			// TODO work on multi-threading
			// every lane a new thread for performance
			// duplicate collection so as to make modification while iterating

			// use executor for each link
			for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator
					.hasNext();) {
				Vehicle v = iterator.next();
				carFollowing.update(v, tracker);
				moving.update(v, odMatrix, tracker);
				logger.info("Time: {}s: {} ", time, v.getName(), +v.position());
				statisticsCollector.visit(tracker, v);
				if (!v.active()) {
					iterator.remove();
				}
			}
			for (Od od : odMatrix.getOds()) {
				List<Vehicle> newVehicles = vehicleGenerating.newVehicles(od,
						network, tracker);
				vehicles.addAll(newVehicles);
			}
			tracker.stepForward();
		}

	}

}
