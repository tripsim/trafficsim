package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Subsegment;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.core.MovingObject;

public class DefaultVehicle extends MovingObject<DefaultVehicle> implements
		Vehicle {

	private static final long serialVersionUID = 1L;

	private VehicleType vehicleType;
	private DriverType driverType;

	private Node destination = null;
	private Link targetLink = null;
	private ConnectionLane preferredConnector = null;
	private Lane currentLane = null;

	// private Lane[] desiredLanes;
	// private double desiredSpeed = 0;

	private double width;
	private double length;

	// private double height;

	public DefaultVehicle(long id, VehicleType vehicleType,
			DriverType driverType, int startFrame) {
		super(id, null, startFrame);
		this.vehicleType = vehicleType;
		this.driverType = driverType;
		this.width = vehicleType.minWidth;
		this.length = vehicleType.minLength;
	}

	@Override
	public Segment getSegment() {
		return currentLane != null ? currentLane.getSegment() : null;
	}

	@Override
	public Subsegment getSubsegment() {
		return currentLane;
	}

	@Override
	public final VehicleType getVehicleType() {
		return vehicleType;
	}

	@Override
	public final void setDriverType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public final DriverType getDriverType() {
		return driverType;
	}

	@Override
	public final void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	@Override
	public final Link getLink() {
		return (Link) getSegment();
	}

	@Override
	public final Lane currentLane() {
		return currentLane;
	}

	@Override
	public final void currentLane(Lane lane) {
		if (currentLane != null)
			currentLane.remove(this);
		if (lane != null)
			lane.add(this);
		this.currentLane = lane;
	}

	@Override
	public final boolean onConnector() {
		return currentLane == null ? false
				: currentLane instanceof ConnectionLane;
	}

	@Override
	public final Link targetLink() {
		return targetLink;
	}

	public void targetLink(Link link) {
		this.targetLink = link;
	}

	@Override
	public final Node destination() {
		return destination;
	}

	@Override
	public final Vehicle leadingVehicle() {
		return currentLane.getLeadingVehicle(this);
	}

	@Override
	public final Vehicle precedingVehicle() {
		return currentLane.getPrecedingVehicle(this);
	}

	@Override
	public void onRefresh() {
		currentLane.update(this);
	}

	/*
	 * Determine the order of the vehicles in the NavigableSet of the lane
	 * Vehicle Queue
	 */
	@Override
	public int compareTo(DefaultVehicle vehicle) {
		return position - vehicle.position() > 0 ? 1 : position
				- vehicle.position() < 0 ? -1 : 0;
	}

	@Override
	public String toString() {
		return getName();
	}

	// TODO move to type
	@Override
	public final double getWidth() {
		return width;
	}

	@Override
	public final double getLength() {
		return length;
	}

	public final void setWidth(double width) {
		this.width = width;
	}

	public final void getLength(double length) {
		this.length = length;
	}

	@Override
	public ConnectionLane preferredConnector() {
		return preferredConnector;
	}

	@Override
	public void preferredConnector(ConnectionLane lane) {
		preferredConnector = lane;
	}

}
