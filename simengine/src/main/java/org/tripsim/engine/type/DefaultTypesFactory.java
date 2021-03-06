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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tripsim.api.model.TypesComposition;
import org.tripsim.api.model.VehicleClass;
import org.tripsim.engine.type.TypesConstant.DriverTypeProperty;
import org.tripsim.engine.type.TypesConstant.VehicleTypeProperty;
import org.tripsim.model.core.CrusingType;
import org.tripsim.model.core.DefaultTypesComposition;

/**
 * A factory for creating DefaultTypes objects.
 * 
 * @author Xuan Shi
 */
@Component("default-types-factory")
class DefaultTypesFactory implements TypesFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultTypesFactory.class);

	@Override
	public NodeType createNodeType(String name) {
		return new NodeType(name);
	}

	@Override
	public LinkType createLinkType(String name) {
		return new LinkType(name);
	}

	@Override
	public VehicleType createVehicleType(String name,
			Map<String, Object> properties) {
		VehicleType type = new VehicleType(name);
		setVehicleTypeProperties(type, properties);
		return type;
	}

	@Override
	public VehicleType createVehicleType(String name, VehicleClass vehicleClass) {
		VehicleType type = new VehicleType(name);
		type.setVehicleClass(vehicleClass);
		return type;
	}

	@Override
	public DriverType createDriverType(String name,
			Map<String, Object> properties) {
		DriverType type = new DriverType(name);
		setDriverTypeProperties(type, properties);
		return type;
	}

	@Override
	public DriverType createDriverType(String name) {
		return createDriverType(name, null);
	}

	@Override
	public TypesComposition createTypesComposition(String name, String[] types,
			Double[] probabilities) {
		return new DefaultTypesComposition(name, types, probabilities);
	}

	private static void setVehicleTypeProperties(VehicleType type,
			Map<String, Object> properties) {
		if (properties == null) {
			logger.debug("no properties to set for vehice type '{}'!",
					type.getName());
			return;
		}
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			VehicleTypeProperty property = TypesConstant
					.getVehicleTypePropertyByKey(entry.getKey());
			Object value = entry.getValue();
			try {
				switch (property) {
				case VEHICLE_CLASS:
					VehicleClass vehicleClass = VehicleClass
							.valueOf((String) value);
					type.setVehicleClass(vehicleClass);
					break;
				case CURISING_TYPE:
					CrusingType crusingType = CrusingType
							.valueOf((String) value);
					type.setCrusingType(crusingType);
					break;
				case WIDTH:
					type.setWidth((Double) value);
					break;
				case LENGTH:
					type.setLength((Double) value);
					break;
				case MAX_ACCEL:
					type.setMaxAccel((Double) value);
					break;
				case MAX_DECEL:
					type.setMaxDecel((Double) value);
					break;
				case MAX_SPEED:
					type.setMaxSpeed((Double) value);
					break;
				default:
					logger.warn("no such property {} for vehicle type!",
							property);
				}
			} catch (IllegalArgumentException | NullPointerException e) {
				logger.debug("invalid value {}!  encountered for property {}!",
						value, property);
			} catch (ClassCastException e) {
				logger.warn(
						"not a valid number {}  for vehcile type property {}!",
						value, property);
			}
		}
	}

	private static void setDriverTypeProperties(DriverType type,
			Map<String, Object> properties) {
		if (properties == null) {
			logger.debug("no properties to set for driver type {}!",
					type.getName());
			return;
		}
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			DriverTypeProperty property = TypesConstant
					.getDriverTypePropertyByKey(entry.getKey());
			Object value = entry.getValue();
			try {
				switch (property) {
				case PERCEPTION_TIME:
					type.setPerceptionTime((Double) value);
					break;
				case REACTION_TIME:
					type.setReactionTime((Double) value);
					break;
				case DESIRED_HEADWAY:
					type.setDesiredHeadway((Double) value);
					break;
				case DESIRED_SPEED:
					type.setDesiredSpeed((Double) value);
					break;
				default:
					logger.warn("no such property {} for vehicle type!",
							property);
				}
			} catch (IllegalArgumentException | NullPointerException e) {
				logger.debug("invalid value {} encountered for property {}!  ",
						value, property);
			} catch (ClassCastException e) {
				logger.warn(
						"not a valid number {} for driver type property {}!",
						value, property);
			}
		}
	}

}
