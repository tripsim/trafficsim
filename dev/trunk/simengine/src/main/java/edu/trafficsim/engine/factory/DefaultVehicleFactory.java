package edu.trafficsim.engine.factory;

import org.apache.commons.math3.random.RandomGenerator;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.roadusers.DefaultVehicle;
import edu.trafficsim.model.util.Randoms;

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
	private static double cv = 0.25;

	@Override
	public Vehicle createVehicle(VehicleType vehicleType,
			DriverType driverType, SimulationScenario scenario)
			throws TransformException {
		// tracking total vehicle num
		// TODO find a better place for this
		count++;
		vid++;
		// vehicle name
		String name = "vehicle" + count;
		// statistics initial frame
		int startFrame = scenario.getSimulator().getForwardedSteps();

		// Normal distribution for desired values
		RandomGenerator rng = scenario.getSimulator().getRand()
				.getRandomGenerator();
		double width = Randoms.normal(vehicleType.getWidth(), cv, rng);
		double length = Randoms.normal(vehicleType.getLength(), cv, rng);
		double maxSpeed = Randoms.normal(vehicleType.getMaxSpeed(), cv, rng);
		double desiredSpeed = Randoms.normal(driverType.getDesiredSpeed(), cv,
				rng);
		double desiredHeadway = Randoms.normal(driverType.getDesiredHeadway(),
				cv, rng);
		double reactionTime = Randoms.normal(driverType.getReactionTime(), cv,
				rng);

		// create vehicle
		DefaultVehicle vehicle = new DefaultVehicle(vid, name, startFrame,
				vehicleType, driverType, width, length, maxSpeed, desiredSpeed,
				desiredHeadway, reactionTime);

		return vehicle;
	}
}
