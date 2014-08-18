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
package edu.trafficsim.engine.factory;

import org.apache.commons.math3.random.RandomGenerator;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.roadusers.DefaultVehicle;
import edu.trafficsim.model.util.Randoms;

/**
 * A factory for creating DefaultVehicle objects.
 * 
 * @author Xuan Shi
 */
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
		int startFrame = scenario.getTimer().getForwardedSteps();

		// Normal distribution for desired values
		RandomGenerator rng = scenario.getTimer().getRand()
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
