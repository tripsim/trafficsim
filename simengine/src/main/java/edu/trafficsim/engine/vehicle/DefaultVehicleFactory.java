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
package edu.trafficsim.engine.vehicle;

import org.apache.commons.math3.random.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.Vehicle;
import edu.trafficsim.engine.simulation.SimulationEnvironment;
import edu.trafficsim.engine.type.DriverType;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.engine.type.VehicleType;
import edu.trafficsim.model.vehicle.DefaultVehicle;
import edu.trafficsim.model.vehicle.DefaultVehicleBuilder;
import edu.trafficsim.util.Randoms;

/**
 * A factory for creating DefaultVehicle objects.
 * 
 * @author Xuan Shi
 */
@Component("default-vechile-factory")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultVehicleFactory implements VehicleFactory {

	@Autowired
	private TypesManager typesManager;

	@Override
	public Vehicle createVehicle(SimulationEnvironment environment,
			Node origin, Node destination, String vehicleTypeName,
			String driverTypeName) {

		long vid = environment.getAndIncrementVehicleCount();
		long startFrame = environment.getForwardedSteps();
		RandomGenerator randomGenerator = environment.getRandomGenerator();
		double sd = environment.getSd();

		VehicleType vehicleType = typesManager.getVehicleType(vehicleTypeName);
		DriverType driverType = typesManager.getDriverType(driverTypeName);

		double width = random(vehicleType.getWidth(), sd, randomGenerator);
		double length = random(vehicleType.getLength(), sd, randomGenerator);
		double maxSpeed = random(vehicleType.getMaxSpeed(), sd, randomGenerator);
		double desiredSpeed = random(driverType.getDesiredSpeed(), sd,
				randomGenerator);
		double desiredHeadway = random(driverType.getDesiredHeadway(), sd,
				randomGenerator);
		double preceptionTime = random(driverType.getPerceptionTime(), sd,
				randomGenerator);
		double reactionTime = random(driverType.getReactionTime(), sd,
				randomGenerator);

		DefaultVehicle vehicle = new DefaultVehicleBuilder(vid,
				vehicleType.getVehicleClass(), vehicleType.getName(),
				driverType.getName()).withStartFrame(startFrame)
				.withWidth(width).withLength(length).withMaxSpeed(maxSpeed)
				.withDesiredSpeed(desiredSpeed)
				.withDesiredHeadway(desiredHeadway)
				.withPerceptionTime(preceptionTime)
				.withReactionTime(reactionTime).withOrigin(origin)
				.withDestination(destination).build();
		return vehicle;
	}

	private double random(double value, double sd,
			RandomGenerator randomGenerator) {
		// Normal distribution for desired values
		return Randoms.normal(value, sd, randomGenerator);
	}
}
