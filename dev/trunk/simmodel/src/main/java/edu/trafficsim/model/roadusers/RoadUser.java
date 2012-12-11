package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.Position;
import edu.trafficsim.model.core.Movable;
import edu.trafficsim.model.core.Trajectory;

public abstract class RoadUser<T> extends BaseEntity<T> implements Movable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Position curPos;
	private Trajectory trajectory;

	public RoadUser(Position position) {
		this.curPos = position;
		trajectory = new Trajectory();
	}
	
	@Override
	public Position getPosition() {
		return curPos;
	}
	
	@Override
	public Trajectory getTrajectory() {
		return trajectory;
	}
	
	public void Move(Position newPos) {
		trajectory.add(curPos);
		curPos = newPos;
	}
	
}
