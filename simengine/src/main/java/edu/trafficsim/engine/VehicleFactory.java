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
package edu.trafficsim.engine;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;

/**
 * A factory for creating Vehicle objects.
 */
public interface VehicleFactory {

	public Vehicle createVehicle(VehicleType vehicleType,
			DriverType driverType, SimulationScenario scenario)
			throws TransformException;;

}
