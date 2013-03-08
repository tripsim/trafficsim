package edu.trafficsim.model.roadusers;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.core.Agent;
import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.Movable;

public abstract class RoadUser<T> extends BaseEntity<T> implements Movable, Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Trajectory trajectory;

	public RoadUser(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public RoadUser(double trajectoryResolution) {
		this(new Trajectory(trajectoryResolution));
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
