package edu.trafficsim.model.demand;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;

public class VehicleToAdd implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final VehicleType vehicleType;
	private final DriverType driverType;
	private final Lane lane;

	private final double initSpeed;
	private final double initAccel;

	public VehicleToAdd(VehicleType vehicleType, DriverType driverType,
			Lane lane, double initSpeed, double initAccel) {
		this.vehicleType = vehicleType;
		this.driverType = driverType;
		this.lane = lane;
		this.initSpeed = initSpeed;
		this.initAccel = initAccel;
	}

	public final VehicleType getVehicleType() {
		return vehicleType;
	}

	public final DriverType getDriverType() {
		return driverType;
	}

	public final Lane getLane() {
		return lane;
	}

	public final double getInitSpeed() {
		return initSpeed;
	}

	public final double getInitAcceleration() {
		return initAccel;
	}

}
