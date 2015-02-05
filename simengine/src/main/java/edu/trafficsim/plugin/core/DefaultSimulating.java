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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.factory.DefaultVehicleFactory;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.ICarFollowing;
import edu.trafficsim.plugin.IMoving;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.IVehicleGenerating;
import edu.trafficsim.plugin.PluginManager;
import edu.trafficsim.utility.Timer;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultSimulating extends AbstractPlugin implements ISimulating {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultSimulating.class);
	
	private static int nThreads = 5;
	
	protected static VehicleFactory vehicleFactory = DefaultVehicleFactory
			.getInstance();

	@Override
	public void run(SimulationScenario simulationScenario,
			StatisticsCollector statistics) throws TransformException {

		// TODO load plug-ins
		IMoving moving = PluginManager.getMovingImpl(null);
		ICarFollowing carFollowing = PluginManager.getCarFollowingImpl(null);
		IVehicleGenerating vehicleGenerating = PluginManager
				.getVehicleGenerating(null);

		Timer timer = simulationScenario.getTimer();
		statistics.begin(timer);
		logger.info("******** Simulation Demo ********");
		logger.info("---- Parameters ----");
		logger.info("Random Seed: " + timer.getSeed());
		logger.info("Step Size: " + timer.getStepSize());
		logger.info("Duration: " + timer.getDuration());

		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		logger.info("---- Simulation ----");
		while (!timer.isFinished()) {
			double time = timer.getForwardedTime();
			// TODO work on multi-threading
			// every lane a new thread for performance
			// duplicate collection so as to make modification while iterating

			
			// use executor for each link
			for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator
					.hasNext();) {
				Vehicle v = iterator.next();
				carFollowing.update(v, simulationScenario);
				moving.update(v, simulationScenario);
				logger.info("Time: " + time + "s: " + v.getName() + " "
						+ v.position());
				if (v.active()) {
//					iterator.remove();
					statistics.visit(v);
				}
			}
			for (Od od : simulationScenario.getOdMatrix().getOds()) {
				List<Vehicle> newVehicles = vehicleGenerating.newVehicles(od,
						simulationScenario, vehicleFactory);
				vehicles.addAll(newVehicles);
			}
			timer.stepForward();
			statistics.stepForward(timer.getForwardedSteps());
		}

		// logger.info("---- Output ----");
		// for (Vehicle v : vehicles) {
		// System.out.print(v.getName() + ": ");
		//
		// for (VehicleState vs : statistics.trajectory(v.getId())) {
		// System.out.print("(" + vs.coord.x + "," + vs.coord.y + ") ");
		// }
		// logger.info();
		// }
		statistics.finish();
		timer.reset();
	}
	
	private void runSimulation() {
		
	}

}
