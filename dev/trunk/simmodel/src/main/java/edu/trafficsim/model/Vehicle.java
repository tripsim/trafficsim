package edu.trafficsim.model;

public interface Vehicle extends Movable, Agent {

	public String getName();

	public VehicleType getVehicleType();

	public void setDriverType(VehicleType vehicleType);

	public DriverType getDriverType();

	public void setDriverType(DriverType driverType);

	public VehicleBehavior getVehicleBehavior();

	public void setVehicleBehavior(VehicleBehavior vehicleBehavior);

	public Lane currentLane();

	public void currentLane(Lane lane);

	public boolean onConnector();

	public Link targetLink();

	public void targetLink(Link link);

	public Node destination();

	public Vehicle leadingVehicle();

	public Vehicle precedingVehicle();

	// TODO remove
	public double getWidth();

	public double getLength();

}
