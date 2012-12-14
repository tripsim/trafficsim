package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.network.Lane;

public class Vehicle extends RoadUser<Vehicle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VehicleType type;
	private double position;
	private Lane lane;

	public Vehicle(VehicleType type, Lane lane) {
		super(lane.getFromLocation().getCoord());
		this.lane = lane;
		this.type = type;
		// TODO: position
	}

	public double getPosition() {
		return position;
	}
	
	public VehicleType getType() {
		return type;
	}
	
	public void setType(VehicleType type) {
		this.type = type;
	}

	public Lane getLane() {
		return lane;
	}
	
	public void setLane(Lane lane) {
		this.lane = lane;
	}
	
	public Vehicle getLeadingVehicle() {
		return null;
	}
	
	public Vehicle getPrecedingVehicle() {
		return null;
	}
	
	@Override
	public int compareTo(Vehicle vehicle) {
		if (vehicle.getLane().equals(lane))
			return super.compareTo(vehicle);
		return position - vehicle.getPosition() > 0 ?
				1 : -1;
	}
}
