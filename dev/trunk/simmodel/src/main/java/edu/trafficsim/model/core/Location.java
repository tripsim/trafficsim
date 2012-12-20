package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.DataContainer;

public interface Location extends DataContainer {

	public Coordinate getCoord();
	
	public void setCoord(Coordinate coord);
	
	public void setCoord(double x, double y);
	
	public String getName();
}