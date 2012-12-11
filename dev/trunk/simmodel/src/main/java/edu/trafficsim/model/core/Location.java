package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface Location extends DataContainer {

	public Position getPosition();
	
	public void setPosition(Position position);
	
	public void setPosition(double x, double y);
	
	public String getName();
}