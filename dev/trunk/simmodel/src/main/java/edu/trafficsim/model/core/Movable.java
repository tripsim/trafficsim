package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.DataContainer;

public interface Movable extends DataContainer {

	public double position();

	public double speed();

	public double acceleration();

	public Coordinate coord();

	public Coordinate[] trajectory();

}