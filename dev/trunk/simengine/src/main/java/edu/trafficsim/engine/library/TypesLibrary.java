package edu.trafficsim.engine.library;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultDriverTypeComposition;
import edu.trafficsim.model.demand.DefaultVehicleTypeComposition;
import edu.trafficsim.utility.Sequence;

public class TypesLibrary extends AbstractTypesLibrary {

	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_CAR_TYPE = "Default Car";
	public static final String DEFAULT_TRUCK_TYPE = "Default Truck";
	public static final String DEFAULT_VEHICLE_TYPE = DEFAULT_CAR_TYPE;
	public static final String DEFAULT_DRIVER_TYPE = "Default Driver";
	public static final String DEFAULT_COMPOSITION = "Default";

	private static VehicleType car;
	private static VehicleType truck;
	private static DriverType driver;
	private static VehicleTypeComposition vehicleComposition;
	private static DriverTypeComposition driverComposition;

	protected TypesLibrary() {
		long internalSeq = 0;
		car = new VehicleType(internalSeq++, DEFAULT_CAR_TYPE, VehicleClass.Car);
		truck = new VehicleType(internalSeq++, DEFAULT_TRUCK_TYPE,
				VehicleClass.Truck);
		driver = new DriverType(internalSeq++, DEFAULT_DRIVER_TYPE);

		vehicleComposition = new DefaultVehicleTypeComposition(internalSeq++,
				DEFAULT_COMPOSITION, new VehicleType[] { car, truck },
				new Double[] { 0.2, 0.8 });
		driverComposition = new DefaultDriverTypeComposition(internalSeq++,
				DEFAULT_COMPOSITION, new DriverType[] { driver },
				new Double[] { 1.0 });
	}

	public static TypesLibrary defaultLibrary(Sequence seq)
			throws ModelInputException {
		TypesLibrary library = new TypesLibrary();

		library.addVehicleType(car);
		library.addVehicleType(truck);
		library.addDriverType(driver);
		library.addVehicleComposition(vehicleComposition);
		library.addDriverComposition(driverComposition);

		return library;
	}

	public VehicleType getDefaultVehicleType() {
		VehicleType type = getVehicleType(DEFAULT_VEHICLE_TYPE);
		if (type != null)
			return type;
		addVehicleType(car);
		return car;
	}

	public DriverType getDefaultDriverType() {
		DriverType type = getDriverType(DEFAULT_DRIVER_TYPE);
		if (type != null)
			return type;
		addDriverType(driver);
		return driver;
	}

	public VehicleTypeComposition getDefaultVehicleComposition() {
		VehicleTypeComposition composition = getVehicleComposition(DEFAULT_COMPOSITION);
		if (composition != null)
			return composition;
		addVehicleType(car);
		addVehicleType(truck);
		addVehicleComposition(vehicleComposition);
		return vehicleComposition;
	}

	public DriverTypeComposition getDefaultDriverComposition() {
		DriverTypeComposition composition = getDriverComposition(DEFAULT_COMPOSITION);
		if (composition != null)
			return composition;
		addDriverType(driver);
		addDriverComposition(driverComposition);
		return composition;
	}
}
