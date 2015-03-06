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
package edu.trafficsim.engine.type;

import java.util.Map;

import edu.trafficsim.api.model.TypesComposition;
import edu.trafficsim.api.model.VehicleClass;

/**
 * A factory for creating Types objects.
 */
public interface TypesFactory {

	NodeType createNodeType(String name);

	LinkType createLinkType(String name);

	VehicleType createVehicleType(String name, Map<String, Object> properties);

	VehicleType createVehicleType(String name, VehicleClass vehicleClass);

	DriverType createDriverType(String name, Map<String, Object> properties);

	DriverType createDriverType(String name);

	TypesComposition createTypesComposition(String name, String[] vehicleTypes,
			Double[] probabilities);
}
