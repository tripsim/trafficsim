package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.Coord;
import edu.trafficsim.model.core.Movable;
import edu.trafficsim.model.core.Trajectory;

public abstract class RoadUser<T> extends BaseEntity<T> implements Movable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Trajectory trajectory;

	public RoadUser(Coord coord) {
		trajectory = new Trajectory(coord);
	}
	
	@Override
	public Coord getCoord() {
		return trajectory.getLastCoord();
	}
	
	@Override
	public Trajectory getTrajectory() {
		return trajectory;
	}

	
}
