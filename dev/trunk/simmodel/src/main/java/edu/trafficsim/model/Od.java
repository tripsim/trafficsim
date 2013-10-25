package edu.trafficsim.model;

public interface Od extends DataContainer {

	public Node getOrigin();

	public Node getDestination();

	public int getVph(double time);

	public VehicleTypeComposition getVehicleTypeComposition(double time);

	public DriverTypeComposition getDriverTypeComposition(double time);
}
