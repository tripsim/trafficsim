package edu.trafficsim.model;


public interface Event extends DataContainer {
	
	public double getStartTime();

	public double getEndTime();
	
	public boolean isActive();

}
