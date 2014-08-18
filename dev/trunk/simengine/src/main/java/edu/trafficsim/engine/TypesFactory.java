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

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

/**
 * A factory for creating Types objects.
 */
public interface TypesFactory {

	public NodeType createNodeType(Sequence seq, String name);

	public NodeType createNodeType(Long id, String name);

	public LinkType createLinkType(Sequence seq, String name);

	public LinkType createLinkType(Long id, String name);

	public VehicleType createVehicleType(Sequence seq, String name,
			VehicleClass vehicleClass);

	public VehicleType createVehicleType(Long id, String name,
			VehicleClass vehicleClass);

	public DriverType createDriverType(Long id, String name);

	public DriverType createDriverType(Sequence seq, String name);

	public VehicleTypeComposition createVehicleTypeComposition(Sequence seq,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException;

	public VehicleTypeComposition createVehicleTypeComposition(Long id,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException;

	public DriverTypeComposition createDriverTypeComposition(Sequence seq,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException;

	public DriverTypeComposition createDriverTypeComposition(Long id,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException;
}
