package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface Agent extends DataContainer {

	public double getStartTime();

	public boolean isActive();
	
	public void apply(Visitor vistor);

}
