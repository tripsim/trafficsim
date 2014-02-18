package edu.trafficsim.engine;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

public interface TypesFactory {

	public VehicleType createVechileType(String name, VehicleClass vehicleClass);

	public DriverType createDriverType(String name);

	public VehicleTypeComposition createVehicleTypeComposition(String name,
			VehicleType[] vehicleTypes, double[] probabilities)
			throws ModelInputException;

	public DriverTypeComposition createDriverTypeComposition(String name,
			DriverType[] driverTypes, double[] probabilities)
			throws ModelInputException;
}
