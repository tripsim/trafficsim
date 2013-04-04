package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface Agent extends DataContainer {
	
	public boolean isActive();
	
	public void stepForward(double stepSize);	
	
}
