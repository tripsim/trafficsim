package edu.trafficsim.model;

public interface Vehicle extends Movable, Agent {

	public VehicleType getVehicleType();

	public void setDriverType(VehicleType vehicleType);

	public DriverType getDriverType();

	public void setDriverType(DriverType driverType);

	public Link getLink();

	public Lane currentLane();

	public void currentLane(Lane lane);

	public boolean onConnector();

	public Link targetLink();

	public void targetLink(Link link);

	public ConnectionLane preferredConnector();

	public void preferredConnector(ConnectionLane lane);

	public Node destination();

	public Vehicle leadingVehicle();

	public Vehicle precedingVehicle();

	// TODO remove
	public double getWidth();

	public double getLength();

}
