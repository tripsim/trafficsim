package edu.trafficsim.model.network;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.roadusers.Vehicle;

public interface Path extends DataContainer {

	/**
	 * Implement our own efficient collection to hold the vehicles within a lane
	 * or link.
	 */

	public Navigable getNavigable();
	
	public Vehicle getHeadVehicle();

	public Vehicle getTailVehicle();

	public Vehicle getLeadingVehicle(Vehicle v);

	public Vehicle getPrecedingVehicle(Vehicle v);

	public void add(Vehicle vehicle);

	public void remove(Vehicle vehicle);
	
	public void refresh(Vehicle vehicle);

	public Vehicle[] getVehicles();

	public double getStart();

	public double getEnd();

	public double getWidth();

	public double getLength();

	public double getShift();

}
