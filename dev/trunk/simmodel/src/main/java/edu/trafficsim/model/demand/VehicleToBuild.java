package edu.trafficsim.model.demand;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;

public class VehicleToBuild implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final VehicleType vehicleType;
	private final DriverType driverType;
	private final Lane lane;
	
	private final double initSpeed;
	private final double initAccel;
	
	public VehicleToBuild (VehicleType vehicleType, DriverType driverType, Lane lane, double initSpeed, double initAccel) {
		this.vehicleType = vehicleType;
		this.driverType = driverType;
		this.lane = lane;
		this.initSpeed = initSpeed;
		this.initAccel = initAccel;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public DriverType getDriverType() {
		return driverType;
	}

	public Lane getLane() {
		return lane;
	}

	public double getInitSpeed() {
		return initSpeed;
	}

	public double getInitAcceleration() {
		return initAccel;
	}
	
}
