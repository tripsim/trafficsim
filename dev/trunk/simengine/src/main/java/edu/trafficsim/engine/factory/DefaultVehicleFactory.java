package edu.trafficsim.engine.factory;

import java.util.NoSuchElementException;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.roadusers.DefaultVehicle;

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
	public Vehicle createVehicle(VehicleSpecs vehicleSpecs,
			SimulationScenario scenario) throws TransformException {

		count++;

		int initFrameId = scenario.getSimulator().getForwardedSteps();

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

		vehicle.position(position);
		String name = "vehicle" + count;
		vehicle.setName(name);

		// TODO routing
		vehicle.currentLane(vehicleSpecs.lane);

		vehicle.refresh();
		return vehicle;
	}
}
