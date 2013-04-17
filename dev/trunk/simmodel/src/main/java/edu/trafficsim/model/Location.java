package edu.trafficsim.model;

import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.Point;


public interface Location extends DataContainer {

	public Point getPoint();

	public double getRadius();
	
	public void transform(CoordinateFilter filter);
}