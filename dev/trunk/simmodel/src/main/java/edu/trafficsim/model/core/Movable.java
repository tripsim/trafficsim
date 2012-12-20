package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.roadusers.Trajectory;

public interface Movable extends DataContainer {

	public Coordinate getCoord();
	
	public Trajectory getTrajectory();
}