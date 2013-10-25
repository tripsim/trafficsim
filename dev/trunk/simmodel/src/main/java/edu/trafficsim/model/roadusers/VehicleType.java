package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.Type;

public class VehicleType extends Type<VehicleType> {
	private static final long serialVersionUID = 1L;

	public static enum VehicleClass {
		Motocycle, Car, Truck, Bus, RV
	}

	public static enum TechnolgyType {
		ConnectedVehicle, CrusieControl, AdaptiveCruiseControl
	}

	double minWidth = 2;
	double maxWidth = 3;
	double minLength = 4;
	double maxLength = 7;
	// private double minHeight;
	// private double maxHeight;
	//
	// private double maxAcceleration;
	// private double maxDeceleration;
	// private double maxSpeed;
	//
	// private double frictions;
	// private double horsePower;
	// private double mpg;
	//
	// private double emission;

	private final VehicleClass vehicleClass;

	public VehicleType(long id, String name, VehicleClass vehicleClass) {
		super(id, name);
		this.vehicleClass = vehicleClass;
	}

	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}

}
