package edu.trafficsim.engine.factory;

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

		// tracking total vehicle num
		// TODO find a better place for this
		count++;

		// statistics init frame
		int initFrameId = scenario.getSimulator().getForwardedSteps();

		// create vehicle, with types, speed, and accel from spec
		DefaultVehicle vehicle = new DefaultVehicle(vid,
				vehicleSpecs.vehicleType, vehicleSpecs.driverType, initFrameId);
		vehicle.speed(vehicleSpecs.initSpeed);
		vehicle.acceleration(vehicleSpecs.initAccel);
		// set vehicle initial position, keep a min headway (gap) from the last
		// vehicle in lane
		double position = 0;
		Vehicle tailVehicle = vehicleSpecs.lane.getTailVehicle();
		if (tailVehicle != null) {
			position = tailVehicle.position()
					- vehicle.getDriverType().getMinHeadway()
					* vehicleSpecs.initSpeed;
			position = position > 0 ? 0 : position;
		}
		vehicle.position(position);
		// set vehicle name
		String name = "vehicle" + count;
		vehicle.setName(name);

		// add vehicle to the current lane
		vehicle.currentLane(vehicleSpecs.lane);

		vehicle.refresh();
		return vehicle;
	}
}
