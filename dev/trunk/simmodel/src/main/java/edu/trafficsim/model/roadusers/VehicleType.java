package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.Type;

public class VehicleType extends Type<VehicleType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static enum VehicleClass {
		Motocycle,
		Car,
		Truck,
		Bus,
		RV
	}
	
	public static enum TechnolgyType {
		ConnectedVehicle,
		CrusieControl,
		AdaptiveCruiseControl
	}

//	private double minWidth;
//	private double maxWidth;
//	private double minLength;
//	private double maxLength;
//	private double minHeight;
//	private double maxHeight;
//	
//	private double maxAcceleration;
//	private double maxDeceleration;
//	private double maxSpeed;
//	
//	private double frictions;
//	private double horsePower;
//	private double mpg;
//	
//	private double emission;
	
	// in seconds
	private double minHeadway;
	
	private final VehicleClass vehicleClass;
	
	public VehicleType(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
		// Hack
		minHeadway = 2;
	}

	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}
	
	public double getMinHeadway() {
		return minHeadway;
	}
	
}
