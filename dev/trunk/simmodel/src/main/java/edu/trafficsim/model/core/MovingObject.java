package edu.trafficsim.model.core;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Agent;
import edu.trafficsim.model.Movable;
import edu.trafficsim.model.Simulator;

public abstract class MovingObject<T> extends BaseEntity<T> implements Movable,
		Agent {

	private static final long serialVersionUID = 1L;

	// TODO remove these two lists
	protected final List<Coordinate> coords = new ArrayList<Coordinate>();
	protected final List<Double> speeds = new ArrayList<Double>();

	private final int startFrame;

	protected double position = 0;
	protected double lateralOffset = 0;

	protected double speed = 0;
	protected double acceleration = 0;

	protected boolean active = true;

	// public MovingObject() {
	// }

	public MovingObject(int startFrame) {
		this.startFrame = startFrame;
	}

	@Override
	public final int getStartFrame() {
		return startFrame;
	}

	@Override
	public final double position() {
		return position;
	}

	public final void position(double position) {
		this.position = position;
	}

	@Override
	public final double speed() {
		return speed;
	}

	public final void speed(double speed) {
		this.speed = speed;
	}

	@Override
	public final double acceleration() {
		return acceleration;
	}

	public final void acceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public double angle() {
		return coords.size() < 2 ? 0 : Angle.angle(
				coords.get(coords.size() - 1), coords.get(coords.size()));
	}

	@Override
	public final Coordinate coord() {
		return coords.size() < 1 ? null : coords.get(coords.size() - 1);
	}

	@Override
	public final Coordinate[] trajectory() {
		return coords.toArray(new Coordinate[0]);
	}
	
	@Override
	public final Double[] speeds() {
		return speeds.toArray(new Double[0]);
	}

	@Override
	public void stepForward(Simulator simulator) {
		if(!active)
			return;
		double stepSize = simulator.getStepSize();
		before();
		update();
		speed += stepSize * acceleration;
		position += stepSize * speed;
		after(simulator);
	}

	@Override
	public final boolean isActive() {
		return active;
	}

	protected abstract void before();

	protected abstract void after(Simulator simulator);

	protected abstract void update();

}
