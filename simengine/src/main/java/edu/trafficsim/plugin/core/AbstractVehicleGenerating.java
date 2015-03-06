package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.TypesComposition;
import edu.trafficsim.api.model.Vehicle;
import edu.trafficsim.engine.simulation.SimulationEnvironment;
import edu.trafficsim.engine.vehicle.VehicleFactory;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.api.IDriver;
import edu.trafficsim.plugin.api.IVehicle;
import edu.trafficsim.plugin.api.IVehicleGenerating;
import edu.trafficsim.util.Randoms;

public abstract class AbstractVehicleGenerating extends AbstractPlugin
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