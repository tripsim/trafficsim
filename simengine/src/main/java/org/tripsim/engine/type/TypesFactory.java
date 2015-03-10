/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.engine.type;

import java.util.Map;

import org.tripsim.api.model.TypesComposition;
import org.tripsim.api.model.VehicleClass;

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
