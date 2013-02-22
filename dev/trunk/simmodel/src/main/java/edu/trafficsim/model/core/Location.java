package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.DataContainer;

public interface Location extends DataContainer {

	public Point getPoint();
	
	public void setPoint(Point coord);
	
	public String getName();
}