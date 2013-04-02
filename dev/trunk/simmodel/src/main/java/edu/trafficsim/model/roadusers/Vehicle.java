package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.behaviors.CarFollowingBehavior;
import edu.trafficsim.model.behaviors.LaneChangingBehavior;
import edu.trafficsim.model.network.Lane;

public class Vehicle extends RoadUser<Vehicle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

// TODO introduce implementation
//	private IVehicle impl;
	
	private VehicleType vehicleType;
	private DriverType driverType;
	
	private CarFollowingBehavior carFollowingBehavior;
	private LaneChangingBehavior laneChangingBehavior;
	
	private boolean active;
	
	private Lane lane;

// TODO incorporate those properties
//	private double width;
//	private double length;
//	private double height;

	public Vehicle(VehicleType vehicleType, DriverType driverType, double startTime, double stepSize) {
		super(startTime, stepSize);
		this.vehicleType = vehicleType;
		this.driverType = driverType;
		this.active = false;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	public void setType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public DriverType getDriverType() {
		return driverType;
	}

	public void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	public Lane getLane() {
		return lane;
	}
	
	public void setLane(Lane lane) {
		this.lane = lane;
	}
	
	public Vehicle getLeadingVehicle() {
		return lane.getLeadingVehicle(this);
	}
	
	public Vehicle getPrecedingVehicle() {
		return lane.getPrecedingVehicle(this);
	}
	
	public CarFollowingBehavior getCarFollowingBehavior() {
		return carFollowingBehavior;
	}

	public void setCarFollowingBehavior(CarFollowingBehavior carFollowingBehavior) {
		this.carFollowingBehavior = carFollowingBehavior;
	}

	public LaneChangingBehavior getLaneChangingBehavior() {
		return laneChangingBehavior;
	}

	public void setLaneChangingBehavior(LaneChangingBehavior laneChangingBehavior) {
		this.laneChangingBehavior = laneChangingBehavior;
	}

	// Determine the order of the vehicles in the NavigableSet of the lane
	// Vehicle Queue
	@Override
	public int compareTo(Vehicle vehicle) {
		if (!vehicle.getLane().equals(lane))
			return super.compareTo(vehicle);
		return position - vehicle.getPosition() > 0 ? 1 : 
			position - vehicle.getPosition() < 0 ? -1 : 0;
	}

	@Override
	public void stepForward() {
		carFollowingBehavior.update(this);
	}
	
	@Override
	public boolean isActive() {
		return active;
	}
}
