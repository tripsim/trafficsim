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
import edu.trafficsim.utility.Sequence;

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
	public VehicleType createVehicleType(Sequence seq, String name,
			VehicleClass vehicleClass) {
		return new VehicleType(seq.nextId(), name, vehicleClass);
	}

	@Override
	public DriverType createDriverType(Sequence seq, String name) {
		return new DriverType(seq.nextId(), name);
	}

	@Override
	public VehicleTypeComposition createVehicleTypeComposition(Sequence seq,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException {
		return new DefaultVehicleTypeComposition(seq.nextId(), name,
				vehicleTypes, probabilities);
	}

	@Override
	public DriverTypeComposition createDriverTypeComposition(Sequence seq,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException {
		return new DefaultDriverTypeComposition(seq.nextId(), name,
				driverTypes, probabilities);
	}

}
