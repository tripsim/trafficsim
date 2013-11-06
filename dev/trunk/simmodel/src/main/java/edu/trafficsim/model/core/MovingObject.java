package edu.trafficsim.model.core;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Agent;
import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Movable;

public abstract class MovingObject<T> extends BaseEntity<T> implements Movable,
		Agent {

	private static final long serialVersionUID = 1L;

	private final int startFrame;

	protected double position;
	protected double lateralOffset;

	protected double speed;
	protected double acceleration;

	protected double angle;
	protected Coordinate coord;

	protected boolean active;

	public MovingObject(long id, String name, int startFrame) {
		super(id, name);
		this.startFrame = startFrame;

		this.position = 0;
		this.lateralOffset = 0;
		this.speed = 0;
		this.acceleration = 0;

		this.angle = 0;
		this.coord = computeCoord();

		this.active = true;
	}

	@Override
	public final int getStartFrame() {
		return startFrame;
	}

	@Override
	public final double position() {
		return position;
	}

	@Override
	public final void position(double position) {
		this.position = position;
	}

	@Override
	public final double speed() {
		return speed;
	}

	@Override
	public final void speed(double speed) {
		this.speed = speed;
	}

	@Override
	public final double acceleration() {
		return acceleration;
	}

	@Override
	public final void acceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public final double angle() {
		return angle;
	}

	@Override
	public final Coordinate coord() {
		return coord;
	}

	@Override
	public final boolean active() {
		return active;
	}

	@Override
	public final void deactivate() {
		this.active = false;
	}

	protected final Coordinate computeCoord() {
		return getSegment().getCoordinate(position,
				lateralOffset + getSubSegment().getShift());
	}
	
	@Override
	public final void refresh() {
		Coordinate newCoord = computeCoord();
		this.angle = Angle.angle(coord, newCoord);
		this.coord = newCoord;
		onRefresh();
	}
	
	abstract protected void onRefresh();

}
