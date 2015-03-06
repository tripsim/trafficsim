/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.model.core;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.api.Agent;
import edu.trafficsim.api.model.Arc;
import edu.trafficsim.api.model.Motion;
import edu.trafficsim.api.model.Movable;
import edu.trafficsim.model.BaseObject;
import edu.trafficsim.model.util.Coordinates;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class MovingObject extends BaseObject implements Movable, Agent {

	private static final long serialVersionUID = 1L;

	private final long startFrame;
	protected boolean active;

	protected final Motion motion;
	protected double angle;
	protected Coordinate coord;

	public MovingObject(long id, long startFrame) {
		super(id);
		this.startFrame = startFrame;
		this.active = true;

		this.motion = new DefaultMotion();
		this.angle = 0;
		this.coord = new Coordinate();
	}

	@Override
	public final long getStartFrame() {
		return startFrame;
	}

	@Override
	public final Motion getMotion() {
		return motion;
	}

	@Override
	public final double getPosition() {
		return motion.getSpeed();
	}

	@Override
	public final void setPosition(double position) {
		motion.setPosition(position);
	}

	@Override
	public double getLateralOffset() {
		return motion.getLateralOffset();
	}

	@Override
	public void setLateralOffset(double lateralOffset) {
		motion.setLateralOffset(lateralOffset);
	}

	@Override
	public double getDirection() {
		return motion.getDirection();
	}

	@Override
	public void setDirection(double direction) {
		motion.setDirection(direction);
	}

	@Override
	public final double getSpeed() {
		return motion.getSpeed();
	}

	@Override
	public final void setSpeed(double speed) {
		motion.setSpeed(speed);
	}

	@Override
	public final double getAcceleration() {
		return motion.getAcceleration();
	}

	@Override
	public final void setAcceleration(double acceleration) {
		motion.setAcceleration(acceleration);
	}

	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public double getLength() {
		return 0;
	}

	@Override
	public final double getAngle() {
		return angle;
	}

	@Override
	public final Coordinate getCoord() {
		return coord;
	}

	@Override
	public final boolean isActive() {
		return active;
	}

	@Override
	public final void deactivate() {
		this.active = false;
	}

	protected final Coordinate computeCoord(Arc arc, double geomPosition) {
		try {
			return getPosition() < 0 ? coord : Coordinates.getOffsetCoordinate(
					arc.getCrs(), arc.getLinearGeom(), geomPosition,
					getLateralOffset());
		} catch (Exception e) {
			throw new RuntimeException(
					"vehicle coordinate transformation failed!", e);
		}
	}

	@Override
	public final void onMoved(Arc arc, double geomPosition) {
		Coordinate newCoord = computeCoord(arc, geomPosition);
		if (motion.getSpeed() > 0) {
			this.angle = Angle.toDegrees(Angle.angle(coord, newCoord));
		}
		this.coord = newCoord;
		onUpdate(motion);
	}

	@Override
	public boolean isAheadOf(Movable movable) {
		compareTo(movable);
		return false;
	}

	/*
	 * Determine the order of the vehicles in the NavigableSet of the lane
	 * Vehicle Queue
	 * 
	 * WARN only works if they are in the same lane, do not rely on this
	 */
	@Override
	public int compareTo(Object mo) {
		if (mo instanceof Movable)
			return motion.getPosition()
					- ((Movable) mo).getMotion().getPosition() > 0 ? 1
					: motion.getPosition()
							- ((Movable) mo).getMotion().getPosition() < 0 ? -1
							: 0;
		throw new ClassCastException("cannot compare Non Movable object!");
	}

	abstract protected void onUpdate(Motion motion);
}
