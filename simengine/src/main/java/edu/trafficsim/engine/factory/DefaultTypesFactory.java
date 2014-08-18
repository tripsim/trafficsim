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

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultDriverTypeComposition;
import edu.trafficsim.model.demand.DefaultVehicleTypeComposition;

/**
 * A factory for creating DefaultTypes objects.
 * 
 * @author Xuan Shi
 */
public class DefaultTypesFactory extends AbstractFactory implements
		TypesFactory {

	private static DefaultTypesFactory factory;

	private DefaultTypesFactory() {
	}

	public static TypesFactory getInstance() {
		if (factory == null)
			factory = new DefaultTypesFactory();
		return factory;
	}

	@Override
	public NodeType createNodeType(Sequence seq, String name) {
		return createNodeType(seq.nextId(), name);
	}

	@Override
	public NodeType createNodeType(Long id, String name) {
		return new NodeType(id, name);
	}

	@Override
	public LinkType createLinkType(Sequence seq, String name) {
		return createLinkType(seq.nextId(), name);
	}

	@Override
	public LinkType createLinkType(Long id, String name) {
		return new LinkType(id, name);
	}

	@Override
	public VehicleType createVehicleType(Sequence seq, String name,
			VehicleClass vehicleClass) {
		return createVehicleType(seq.nextId(), name, vehicleClass);
	}

	@Override
	public VehicleType createVehicleType(Long id, String name,
			VehicleClass vehicleClass) {
		return new VehicleType(id, name, vehicleClass);
	}

	@Override
	public DriverType createDriverType(Sequence seq, String name) {
		return createDriverType(seq.nextId(), name);
	}

	@Override
	public DriverType createDriverType(Long id, String name) {
		return new DriverType(id, name);
	}

	@Override
	public VehicleTypeComposition createVehicleTypeComposition(Sequence seq,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException {
		return createVehicleTypeComposition(seq.nextId(), name, vehicleTypes,
				probabilities);
	}

	@Override
	public VehicleTypeComposition createVehicleTypeComposition(Long id,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException {
		return new DefaultVehicleTypeComposition(id, name, vehicleTypes,
				probabilities);
	}

	@Override
	public DriverTypeComposition createDriverTypeComposition(Sequence seq,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException {
		return createDriverTypeComposition(seq.nextId(), name, driverTypes,
				probabilities);
	}

	@Override
	public DriverTypeComposition createDriverTypeComposition(Long id,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException {
		return new DefaultDriverTypeComposition(id, name, driverTypes,
				probabilities);
	}

}