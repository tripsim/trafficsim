package edu.trafficsim.model;

import com.vividsolutions.jts.geom.Coordinate;


public interface Movable extends DataContainer {

	public double position();

	public double speed();

	public double acceleration();

	public Coordinate coord();

	public Coordinate[] trajectory();

}