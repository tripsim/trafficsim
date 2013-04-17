package edu.trafficsim.model;

import edu.trafficsim.model.behaviors.CarFollowingBehavior;
import edu.trafficsim.model.behaviors.LaneChangingBehavior;
import edu.trafficsim.model.core.Movable;
import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;

public interface Vehicle extends Movable, Agent {

	public String getName();

	public VehicleType getVehicleType();

	public DriverType getDriverType();

	public Lane getLane();

	public boolean onConnector();

	public Link getTargetLink();

	public Node getDestination();

	public Vehicle getLeadingVehicle();

	public Vehicle getPrecedingVehicle();

	public boolean beyondEnd();

	public void route(Simulator simulator);

	public CarFollowingBehavior getCarFollowingBehavior();

	public LaneChangingBehavior getLaneChangingBehavior();

}
