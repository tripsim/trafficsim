package edu.trafficsim.model;

import com.vividsolutions.jts.geom.Coordinate;


public interface Movable extends DataContainer {

	public double position();

	public double speed();

	public double acceleration();

	public Coordinate coord();
	
	public double angle();

	// TODO create a class to hold vehicle statistics
	public Coordinate[] trajectory();
	
	public Double[] speeds();

}