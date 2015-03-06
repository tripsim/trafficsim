package edu.trafficsim.model.core;

import java.io.Serializable;

import edu.trafficsim.api.model.Motion;

public class DefaultMotion implements Motion, Serializable {
	private static final long serialVersionUID = 1L;

	double position = -1;
	double lateralOffset = 0;
	double direction = 0;

	double speed = 0;
	double acceleration = 0;

	public DefaultMotion() {
	}

	public DefaultMotion(Motion motion) {
		this.position = motion.getPosition();
		this.lateralOffset = motion.getLateralOffset();
		this.direction = motion.getDirection();
		this.speed = motion.getSpeed();
		this.acceleration = motion.getAcceleration();
	}

	public DefaultMotion(double position, double speed) {
		this.position = position;
		this.speed = speed;
	}

	@Override
	public final double getPosition() {
		return position;
	}

	@Override
	public final void setPosition(double position) {
		this.position = position;
	}

	@Override
	public double getLateralOffset() {
		return lateralOffset;
	}

	@Override
	public void setLateralOffset(double lateralOffset) {
		this.lateralOffset = lateralOffset;
	}

	@Override
	public double getDirection() {
		return direction;
	}

	@Override
	public void setDirection(double direction) {
		this.direction = direction;
	}

	@Override
	public final double getSpeed() {
		return speed;
	}

	@Override
	public final void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public final double getAcceleration() {
		return acceleration;
	}

	@Override
	public final void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public void update(Motion motion) {
		this.position = motion.getPosition();
		this.lateralOffset = motion.getLateralOffset();
		this.direction = motion.getDirection();
		this.speed = motion.getSpeed();
		this.acceleration = motion.getAcceleration();
	}

	@Override
	public String toString() {
		return "DefaultMotion [position=" + position + ", lateralOffset="
				+ lateralOffset + ", direction=" + direction + ", speed="
				+ speed + ", acceleration=" + acceleration + "]";
	}

}
