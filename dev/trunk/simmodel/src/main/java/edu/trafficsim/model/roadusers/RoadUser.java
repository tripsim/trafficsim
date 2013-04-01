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
	

	protected double position;
	protected double speed;
	protected double acceleration;

	public RoadUser(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public RoadUser(double startTime, double stepSize) {
		this(new Trajectory(startTime, stepSize));
	}
	
	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
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
