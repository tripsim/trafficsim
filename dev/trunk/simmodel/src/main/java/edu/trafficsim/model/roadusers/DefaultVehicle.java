package edu.trafficsim.model.roadusers;

import java.util.Collection;

import edu.trafficsim.model.CarFollowingBehavior;
import edu.trafficsim.model.Connector;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.LaneChangingBehavior;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.behaviors.DefaultLaneChanging;
import edu.trafficsim.model.core.MovingObject;

public class DefaultVehicle extends MovingObject<DefaultVehicle> implements
		Vehicle {

	private static final long serialVersionUID = 1L;

	private VehicleType vehicleType;
	private DriverType driverType;

	private CarFollowingBehavior carFollowingBehavior;
	private LaneChangingBehavior laneChangingBehavior;

	private Node destination = null;
	private Link targetLink = null;
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
	public final VehicleType getVehicleType() {
		return vehicleType;
	}

	public final void setType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public final DriverType getDriverType() {
		return driverType;
	}

	public final void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	@Override
	public final Lane getLane() {
		return currentLane;
	}

	public final void setLane(Lane lane) {
		this.currentLane = lane;
	}

	@Override
	public final boolean onConnector() {
		return currentLane.getSegment() instanceof Connector;
	}

	@Override
	public final Link getTargetLink() {
		return targetLink;
	}

	@Override
	public final Node getDestination() {
		return destination;
	}

	public final Vehicle getLeadingVehicle() {
		return currentLane.getLeadingVehicle(this);
	}

	public final Vehicle getPrecedingVehicle() {
		return currentLane.getPrecedingVehicle(this);
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
			DefaultLaneChanging laneChangingBehavior) {
		this.laneChangingBehavior = laneChangingBehavior;
	}

	// Determine the order of the vehicles in the NavigableSet of the lane
	// Vehicle Queue
	@Override
	public int compareTo(DefaultVehicle vehicle) {
		return position - vehicle.position() > 0 ? 1 : position
				- vehicle.position() < 0 ? -1 : 0;
	}

	@Override
	public void update() {
		carFollowingBehavior.update(this);
		// laneChangingBehavior.update(this);
	}

	@Override
	public boolean beyondEnd() {
		return currentLane.getLength() - position > 0 ? false : true;
	}

	@Override
	protected void before() {
		coords.add(currentLane.getSegment().getCoordinate(position,
				lateralOffset + currentLane.getShift()));
		speeds.add(speed);
	}

	@Override
	protected void after(Simulator simulator) {
		currentLane.refresh(this);
		while (isActive() && beyondEnd()) {
			convey(simulator);
		}
	}

	// TODO move vehicle moving logic out
	@Override
	public void route(Simulator simulator) {
		if (!(currentLane.getSegment() instanceof Link)) {
			targetLink = null;
			return;
		}
		// Router router = ((Link) currentLane.getSegment()).getEndNode()
		// .getRouter();
		// targetLink = router == null ? null : router.getSucceedingLink(this,
		// simulator);
	}

	private void convey(Simulator simulator) {
		currentLane.remove(this);
		if (targetLink == null) {
			active = false;
			return;
		}
		System.out.println("------- Debug Convey --------");
		position -= currentLane.getLength();
		Segment segment = currentLane.getSegment();
		if (segment instanceof Link) {
			Collection<Connector> connectors = ((Link) segment).getEndNode()
					.getConnectors(currentLane);
			if (connectors == null) {
				active = false;
				targetLink = null;
				return;
			}
			for (Connector connector : connectors) {
				if (connector.getFromLane().equals(currentLane)) {
					currentLane = connector.getLane();
					currentLane.add(this);
					return;
				}
			}
			// TODO access to next link not found
			System.out.print("Fail to convey the vehicle to target link");
		} else if (segment instanceof Connector) {
			currentLane = ((Connector) segment).getToLane();
			currentLane.add(this);
			route(simulator);
		}
	}

	@Override
	public String toString() {
		return getName();
	}

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
}
