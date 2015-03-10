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
package org.tripsim.web.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.VehicleClass;
import org.tripsim.engine.type.DriverType;
import org.tripsim.engine.type.TypesFactory;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.engine.type.VehicleType;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("types-service")
public class TypesService extends EntityService {

	@Autowired
	TypesManager typesManager;
	@Autowired
	TypesFactory typesFactory;

	public VehicleType createVehicleType(String name, VehicleClass vehicleClass) {
		VehicleType type = typesManager.getVehicleType(name);
		if (type != null) {
			throw new IllegalArgumentException("Vehicle type '" + name
					+ "' already exists.");
		}
		type = typesFactory.createVehicleType(name, vehicleClass);
		typesManager.saveVehicleType(type);
		return type;
	}

	public void removeVehicleType(String name) {
		if (typesManager.countCompositionWithVehicleType(name) != 0) {
			throw new RuntimeException("Cannot remove vehicle type '" + name
					+ "', it is referenced.");
		}
		typesManager.deleteVehicleType(name);
	}

	public VehicleType updateVehicleType(String name, String newName,
			VehicleClass vehicleClass, double width, double length,
			double maxAccel, double maxDecel, double maxSpeed) {
		if (!name.equals(newName)
				&& typesManager.getVehicleType(newName) != null) {
			throw new IllegalArgumentException("Vehicle type '" + newName
					+ "' already exists.");
		}
		VehicleType type = typesManager.getVehicleType(name);
		type.setName(newName);
		type.setVehicleClass(vehicleClass);
		type.setWidth(width);
		type.setLength(length);
		type.setMaxAccel(maxAccel);
		type.setMaxDecel(maxDecel);
		type.setMaxSpeed(maxSpeed);
		typesManager.saveVehicleType(type);
		return type;
	}

	public DriverType createDriverType(String name) {
		DriverType type = typesManager.getDriverType(name);
		if (type != null) {
			throw new IllegalArgumentException("Driver type '" + name
					+ "' already exists.");
		}
		type = typesFactory.createDriverType(name);
		typesManager.saveDriverType(type);
		return type;
	}

	public void removeDriverType(String name) {
		if (typesManager.countCompositionWithDriverType(name) != 0) {
			throw new RuntimeException("Cannot remove driver type '" + name
					+ "', it is referenced.");
		}
		typesManager.deleteDriverType(name);
	}

	public DriverType updateDriverType(String name, String newName,
			double perceptionTime, double reactionTime, double desiredHeadway,
			double desiredSpeed) {
		if (!name.equals(newName)
				&& typesManager.getDriverType(newName) != null) {
			throw new IllegalArgumentException("Driver type '" + name
					+ "' already exists.");
		}
		DriverType type = typesManager.getDriverType(name);
		type.setPerceptionTime(perceptionTime);
		type.setReactionTime(reactionTime);
		type.setDesiredHeadway(desiredHeadway);
		type.setDesiredSpeed(desiredSpeed);
		typesManager.saveDriverType(type);
		return type;
	}
}
