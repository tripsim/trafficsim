package edu.trafficsim.engine;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.Sequence;

public interface TypesFactory {

	public VehicleType createVehicleType(Sequence seq, String name,
			VehicleClass vehicleClass);

	public DriverType createDriverType(Sequence seq, String name);

	public VehicleTypeComposition createVehicleTypeComposition(Sequence seq,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException;

	public DriverTypeComposition createDriverTypeComposition(Sequence seq,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException;

}
