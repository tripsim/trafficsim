package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.behaviors.CarFollowingBehavior;
import edu.trafficsim.model.behaviors.LaneChangingBehavior;
import edu.trafficsim.model.core.MovingObject;
import edu.trafficsim.model.core.Visitor;
import edu.trafficsim.model.network.Path;

public class Vehicle extends MovingObject<Vehicle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO introduce implementation
	// private IVehicle impl;

	private VehicleType vehicleType;
	private DriverType driverType;

	private CarFollowingBehavior carFollowingBehavior;
	private LaneChangingBehavior laneChangingBehavior;

	// private Lane desiredLane = null;
	// private double desiredSpeed = 0;

	private Path path;

	// TODO incorporate those properties
	// private double width;
	// private double length;
	// private double height;

	public Vehicle(VehicleType vehicleType, DriverType driverType,
			double startTime) {
		super(startTime);
		this.vehicleType = vehicleType;
		this.driverType = driverType;
	}

	public final VehicleType getVehicleType() {
		return vehicleType;
	}

	public final void setType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public final DriverType getDriverType() {
		return driverType;
	}

	public final void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	public final Path getPath() {
		return path;
	}

	public final void setPath(Path path) {
		this.path = path;
	}

	public final Vehicle getLeadingVehicle() {
		return path.getLeadingVehicle(this);
	}

	public final Vehicle getPrecedingVehicle() {
		return path.getPrecedingVehicle(this);
	}

	public final CarFollowingBehavior getCarFollowingBehavior() {
		return carFollowingBehavior;
	}

	public final void setCarFollowingBehavior(
			CarFollowingBehavior carFollowingBehavior) {
		this.carFollowingBehavior = carFollowingBehavior;
	}

	public final LaneChangingBehavior getLaneChangingBehavior() {
		return laneChangingBehavior;
	}

	public final void setLaneChangingBehavior(
			LaneChangingBehavior laneChangingBehavior) {
		this.laneChangingBehavior = laneChangingBehavior;
	}

	// Determine the order of the vehicles in the NavigableSet of the lane
	// Vehicle Queue
	@Override
	public int compareTo(Vehicle vehicle) {
		if (!vehicle.getPath().equals(path))
			return super.compareTo(vehicle);
		return position - vehicle.position() > 0 ? 1 : position
				- vehicle.position() < 0 ? -1 : 0;
	}

	@Override
	public void update() {
		// carFollowingBehavior.update(this);
		// laneChangingBehavior.update(this);
	}

	@Override
	public boolean beyondEnd() {
		return path.getLength() - position > 0 ? false : true;
	}

	@Override
	protected void before() {
		coords.add(path.getNavigable().getCoordinate(position,
				lateralOffset + path.getShift()));
	}

	@Override
	protected void after() {
		path.refresh(this);
	}
	
	
	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
