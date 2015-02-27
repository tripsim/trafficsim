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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.trafficsim.engine.simulation.Tracker;
import edu.trafficsim.engine.type.DriverType;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.engine.type.VehicleType;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.roadusers.DefaultVehicle;
import edu.trafficsim.model.roadusers.DefaultVehicleBuilder;
import edu.trafficsim.model.util.Randoms;

/**
 * A factory for creating DefaultVehicle objects.
 * 
 * @author Xuan Shi
 */
@Component("default-vechile-factory")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultVehicleFactory implements VehicleFactory {

	private static final String DEFAULT_NAME_PRE = "vehicle-";

	@Autowired
	private TypesManager typesManager;

	@Override
	public Vehicle createVehicle(Node origin, Node destination,
			String vehicleTypeName, String driverTypeName, Tracker tracker) {

		long vid = (long) tracker.getAndIncrementVehicleCount();
		String name = DEFAULT_NAME_PRE + vid;
		long startFrame = tracker.getForwardedSteps();

		VehicleType vehicleType = typesManager.getVehicleType(vehicleTypeName);
		DriverType driverType = typesManager.getDriverType(driverTypeName);

		double width = random(vehicleType.getWidth(), tracker);
		double length = random(vehicleType.getLength(), tracker);
		double maxSpeed = random(vehicleType.getMaxSpeed(), tracker);
		double desiredSpeed = random(driverType.getDesiredSpeed(), tracker);
		double desiredHeadway = random(driverType.getDesiredHeadway(), tracker);
		double preceptionTime = random(driverType.getPerceptionTime(), tracker);
		double reactionTime = random(driverType.getReactionTime(), tracker);

		DefaultVehicle vehicle = new DefaultVehicleBuilder(vid, name,
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

	private double random(double value, Tracker tracker) {
		// Normal distribution for desired values
		return Randoms.normal(value, tracker.getSd(), tracker.getRand()
				.getRandomGenerator());
	}
}
