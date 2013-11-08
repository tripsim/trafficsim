package edu.trafficsim.engine.factory;

import java.util.NoSuchElementException;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.model.CarFollowingType;
import edu.trafficsim.model.LaneChangingType;
import edu.trafficsim.model.MovingType;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleBehavior;
import edu.trafficsim.model.roadusers.DefaultVehicle;
import edu.trafficsim.model.roadusers.DefaultVehicleBehavior;

public class DefaultVehicleFactory extends AbstractFactory implements
		VehicleFactory {

	private static DefaultVehicleFactory factory;

	private static int count = 0;

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
	public Vehicle createVehicle(VehicleSpecs vehicleSpecs, Simulator simulator) {
		count++;

		double startTime = simulator.getForwardedTime();
		double stepSize = simulator.getStepSize();
		int initFrameId = (int) Math.round(startTime / stepSize);

		DefaultVehicle vehicle = new DefaultVehicle(vid,
				vehicleSpecs.vehicleType, vehicleSpecs.driverType, initFrameId);
		vehicle.speed(vehicleSpecs.initSpeed);
		vehicle.acceleration(vehicleSpecs.initAccel);
		double position = 0;
		try {
			Vehicle tailVehicle = vehicleSpecs.lane.getTailVehicle();
			position = tailVehicle.position()
					- vehicle.getDriverType().getMinHeadway()
					* vehicleSpecs.initSpeed;
			position = position > 0 ? 0 : position;
		} catch (NoSuchElementException e) {
		}
		vehicle.setVehicleBehavior(vehicleSpecs.vehicleBehavior);

		vehicle.position(position);
		String name = "vehicle" + count;
		vehicle.setName(name);
		vehicle.currentLane(vehicleSpecs.lane);
		
		vehicle.refresh();
		return vehicle;
	}

	@Override
	public VehicleBehavior createBehavior(String name, MovingType movingType,
			CarFollowingType carFollowingType, LaneChangingType laneChangingType) {
		return new DefaultVehicleBehavior(nextId(), name, movingType,
				carFollowingType, laneChangingType);
	}
}
