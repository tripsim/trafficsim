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
package org.tripsim.plugin.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.TypesComposition;
import org.tripsim.api.model.Vehicle;
import org.tripsim.engine.simulation.SimulationEnvironment;
import org.tripsim.engine.vehicle.VehicleFactory;
import org.tripsim.plugin.AbstractPlugin;
import org.tripsim.plugin.api.IDriver;
import org.tripsim.plugin.api.IVehicle;
import org.tripsim.plugin.api.IVehicleGenerating;
import org.tripsim.util.Randoms;

abstract class AbstractVehicleGenerating extends AbstractPlugin
		implements IVehicleGenerating {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractVehicleGenerating.class);

	@Autowired
	VehicleFactory vehicleFactory;

	@Override
	public final List<Vehicle> newVehicles(SimulationEnvironment environment,
			Od od) {
		try {
			GeneratingEnvironment generatingEnvironment = new GeneratingEnvironment(
					environment, od);
			return generatingEnvironment.generateVehicles();
		} catch (RuntimeException e) {
			logger.warn("No vehicle is created due to {}", e);
			return Collections.emptyList();
		}
	}

	protected class GeneratingEnvironment {

		final int vph;

		SimulationEnvironment environment;
		final Node origin;
		final Node destination;
		final TypesComposition vehicleTypesComposition;
		final TypesComposition driverTypesComposition;

		GeneratingEnvironment(SimulationEnvironment environment, Od od) {
			this.environment = environment;
			Network network = environment.getNetwork();
			origin = network.getNode(od.getOriginNodeId());
			if (origin == null) {
				throw new IllegalStateException("Node " + od.getOriginNodeId()
						+ " not exists in network " + network.getName() + ".");
			}
			if (origin.getDownstreams().isEmpty()) {
				throw new IllegalStateException("Node " + od.getOriginNodeId()
						+ " doesn't have downstream links!");
			}
			destination = od.getDestinationNodeId() == null ? null : network
					.getNode(od.getDestinationNodeId());
			vph = od.vph(environment.getForwardedTime());
			vehicleTypesComposition = od.getVehicleComposition();
			driverTypesComposition = od.getDriverComposition();
		}

		List<Vehicle> generateVehicles() {
			int num = numToGenerate(vph, environment.getStepSize(),
					environment.getRandom(), environment.getRandomGenerator());
			if (num < 1) {
				logger.debug("Time {}s: no vehicles to generate at ",
						environment.getForwardedTime());
				return Collections.emptyList();
			}
			return generateVehicles(num);
		}

		List<Vehicle> generateVehicles(int num) {
			List<Vehicle> newVehicles = new ArrayList<Vehicle>();
			for (int i = 0; i < num; i++) {
				Vehicle vehicle = createVehicle();
				if (vehicle == null) {
					continue;
				}
				initMotion(vehicle);
				newVehicles.add(vehicle);
			}
			return newVehicles;
		}

		private Vehicle createVehicle() {
			// create vehicle with random vehicle type and driver type
			String vtypeToBuild = Randoms.randomElement(
					vehicleTypesComposition, environment.getRandom());
			String dtypeToBuild = Randoms.randomElement(driverTypesComposition,
					environment.getRandom());
			if (vtypeToBuild == null || dtypeToBuild == null) {
				return null;
			}

			Vehicle vehicle = vehicleFactory.createVehicle(environment, origin,
					destination, vtypeToBuild, dtypeToBuild);
			afterCreatingVehicle(environment, vehicle);
			return vehicle;
		}

		private void initMotion(Vehicle vehicle) {
			IVehicle vehicleImpl = environment.getVehicleImpl(vehicle
					.getVehicleType());
			IDriver driverImpl = environment.getDriverImpl(vehicle
					.getDriverType());
			double speed = initSpeed(vehicle.getMaxSpeed(),
					vehicle.getDesiredSpeed(), environment.getRandomGenerator());
			double accel = initAccel(vehicleImpl.getMaxAccel(speed,
					vehicle.getMaxSpeed()), driverImpl.getDesiredAccel(speed,
					vehicle.getDesiredSpeed()),
					environment.getRandomGenerator());
			vehicle.setSpeed(speed);
			vehicle.setAcceleration(accel);
		}

	}

	protected abstract int numToGenerate(int vph, double stepSize,
			Random random, RandomGenerator rng);

	protected abstract double initSpeed(double maxSpeed, double desiredSpeed,
			RandomGenerator rng);

	protected abstract double initAccel(double maxAccel, double desiredAccel,
			RandomGenerator rng);

	private void afterCreatingVehicle(SimulationEnvironment environment,
			Vehicle vehicle) {
		logger.debug("Time: {}s -- New Vehicle created: {} --> {} --> {}",
				environment.getForwardedTime(), vehicle.getId(),
				vehicle.getVehicleType(), vehicle.getDriverType());
	}

}
