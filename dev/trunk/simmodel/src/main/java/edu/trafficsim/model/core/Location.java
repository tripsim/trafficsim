package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.DataContainer;

public interface Location extends DataContainer {

	public Point getPoint();

	public double getRadius();
}