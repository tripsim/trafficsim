package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface Movable extends DataContainer {

	public Coord getCoord();
	
	public Trajectory getTrajectory();
}