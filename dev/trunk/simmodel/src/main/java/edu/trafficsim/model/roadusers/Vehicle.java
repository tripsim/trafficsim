package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.behaviors.CarFollowingBehavior;
import edu.trafficsim.model.behaviors.LaneChangingBehavior;
import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.plugin.IVehicle;

public class Vehicle extends RoadUser<Vehicle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IVehicle impl;
	
	private VehicleType vehicleType;
	private DriverType driverType;
	
	private CarFollowingBehavior carFollowingBehavior;
	private LaneChangingBehavior laneChangingBehavior;
	
	private double position;
	private Lane lane;

	public Vehicle(VehicleType vehicleType, DriverType driverType, Lane lane) {
		super(lane.getFromLocation().getCoord());
		this.lane = lane;
		this.vehicleType = vehicleType;
		this.driverType = driverType;
		// TODO: position
	}

	public double getPosition() {
		return position;
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
	
	public Integer getNeighborhoodIndex() {
		lane.getLink();
		return new Integer((int) (position / Link.NEIBOUR_SIZE));
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

	@Override
	public int compareTo(Vehicle vehicle) {
		if (!vehicle.getLane().equals(lane))
			return super.compareTo(vehicle);
		return position - vehicle.getPosition() > 0 ? 1 : 
			position - vehicle.getPosition() < 0 ? -1 : 0;
	}
}
