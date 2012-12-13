package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface Location extends DataContainer {

	public Coord getCoord();
	
	public void setCoord(Coord coord);
	
	public void setCoord(double x, double y);
	
	public String getName();
}