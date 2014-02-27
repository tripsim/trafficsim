package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.Type;

public class VehicleType extends Type<VehicleType> {
	private static final long serialVersionUID = 1L;

	public static enum VehicleClass {
		Motocycle, Car, Truck, Bus, RV
	}

	public static enum TechnolgyType {
		ConnectedVehicle, CrusieControl, AdaptiveCruiseControl
	}

	// TODO make it a distribution
	double width = 2.5;
	double length = 4.5;

	// TODO make it a function
	double maxAccel = 3.5;
	double maxDecel = -7.5;
	double maxSpeed = 40;
	// private double minHeight;
	// private double maxHeight;
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getMaxAccel() {
		return maxAccel;
	}

	public void setMaxAccel(double maxAccel) {
		this.maxAccel = maxAccel;
	}

	public double getMaxDecel() {
		return maxDecel;
	}

	public void setMaxDecel(double maxDecel) {
		this.maxDecel = maxDecel;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

}
