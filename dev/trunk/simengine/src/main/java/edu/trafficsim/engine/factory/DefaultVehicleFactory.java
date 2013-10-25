package edu.trafficsim.engine.factory;

import java.util.NoSuchElementException;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.generator.VehicleToAdd;
import edu.trafficsim.model.CarFollowingBehavior;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.behaviors.DefaultCarFollowing;
import edu.trafficsim.model.roadusers.DefaultVehicle;

public class DefaultVehicleFactory extends AbstractFactory implements
		VehicleFactory {

	private static DefaultVehicleFactory factory;

	private static int count = 0;

	// HACK
	CarFollowingBehavior carFollowing = new DefaultCarFollowing(0, "temp");

	private DefaultVehicleFactory() {
	}

	public static DefaultVehicleFactory getInstance() {
		if (factory == null)
			factory = new DefaultVehicleFactory();
		return factory;
	}

	// TODO complete the creation
	private static long vid = 0;

	@Override
	public Vehicle createVehicle(VehicleToAdd vehicleToAdd, Simulator simulator) {
		count++;

		double startTime = simulator.getForwarded();
		double stepSize = simulator.getStepSize();
		int initFrameId = (int) Math.round(startTime / stepSize);

		DefaultVehicle vehicle = new DefaultVehicle(vid,
				vehicleToAdd.getVehicleType(), vehicleToAdd.getDriverType(),
				initFrameId);
		vehicle.speed(vehicleToAdd.getInitSpeed());
		vehicle.acceleration(vehicleToAdd.getInitAcceleration());
		double position = 0;
		try {
			Vehicle tailVehicle = vehicleToAdd.getLane().getTailVehicle();
			position = tailVehicle.position()
					- vehicle.getDriverType().getMinHeadway()
					* vehicleToAdd.getInitSpeed();
			position = position > 0 ? 0 : position;
		} catch (NoSuchElementException e) {
		}
		vehicle.setCarFollowingBehavior(carFollowing);
		vehicle.position(position);
		String name = "vehicle" + count;
		vehicle.setName(name);
		vehicle.setLane(vehicleToAdd.getLane());
		vehicleToAdd.getLane().add(vehicle);
		vehicle.route(simulator);
		return vehicle;
	}

}
