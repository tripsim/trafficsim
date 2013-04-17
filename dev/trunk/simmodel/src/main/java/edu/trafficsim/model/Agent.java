package edu.trafficsim.model;



public interface Agent extends DataContainer {

	public double getStartTime();

	public boolean isActive();
	
	public void stepForward(Simulator simulator);

}
