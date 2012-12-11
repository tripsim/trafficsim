package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface Segment extends DataContainer {
	
	public Location getFromLocation();
	
	public Location getToLocation();
	
	public void setFromLocation(Location fromLocation);
	
	public void setToLocation(Location toLocation);
	
	public String getName();

	// TODO: Geometry
}
