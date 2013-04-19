package edu.trafficsim.model;



public interface Agent extends DataContainer {

	public int getStartFrame();

	public boolean isActive();
	
	public void stepForward(Simulator simulator);

}
