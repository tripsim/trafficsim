package edu.trafficsim.engine.factory;

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultDriverTypeComposition;
import edu.trafficsim.model.demand.DefaultVehicleTypeComposition;

public class DefaultTypesFactory extends AbstractFactory implements
		TypesFactory {

	private static TypesFactory factory;

	private DefaultTypesFactory() {
	}

	public static TypesFactory getInstance() {
		if (factory == null)
			factory = new DefaultTypesFactory();
		return factory;
	}

	@Override
	public VehicleType createVechileType(String name, VehicleClass vehicleClass) {
		return new VehicleType(nextId(), name, vehicleClass);
	}

	@Override
	public DriverType createDriverType(String name) {
		return new DriverType(nextId(), name);
	}

	@Override
	public VehicleTypeComposition createVehicleTypeComposition(String name,
			VehicleType[] vehicleTypes, double[] probabilities)
			throws ModelInputException {
		return new DefaultVehicleTypeComposition(nextId(), name, vehicleTypes,
				probabilities);
	}

	@Override
	public DriverTypeComposition createDriverTypeComposition(String name,
			DriverType[] driverTypes, double[] probabilities)
			throws ModelInputException {
		return new DefaultDriverTypeComposition(nextId(), name, driverTypes,
				probabilities);
	}

}
