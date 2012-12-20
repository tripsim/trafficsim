package edu.trafficsim.model.roadusers;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.Movable;

public abstract class RoadUser<T> extends BaseEntity<T> implements Movable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Trajectory trajectory;

	public RoadUser() {
		
	}
	
	public RoadUser(Coordinate coord) {
		trajectory = new Trajectory(coord);
	}
	
	@Override
	public Coordinate getCoord() {
		return trajectory == null ? null : trajectory.getLastCoord();
	}
	
	@Override
	public Trajectory getTrajectory() {
		return trajectory;
	}

	
}
