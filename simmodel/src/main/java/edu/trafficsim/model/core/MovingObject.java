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

import edu.trafficsim.model.Agent;
import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Movable;
import edu.trafficsim.model.util.Coordinates;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class MovingObject<T> extends BaseEntity<T> implements Movable,
		Agent {

	private static final long serialVersionUID = 1L;

	private final long startFrame;

	protected double position;
	protected double lateralOffset;

	protected double speed;
	protected double acceleration;

	protected double angle;
	protected Coordinate coord;

	protected boolean active;

	public MovingObject(long id, String name, long startFrame) {
		super(id, name);
		this.startFrame = startFrame;

		this.position = 0;
		this.lateralOffset = 0;
		this.speed = 0;
		this.acceleration = 0;

		this.angle = 0;
		this.coord = new Coordinate();

		this.active = true;
	}

	@Override
	public final long getStartFrame() {
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

	protected final Coordinate computeCoord() throws ModelInputException {
		try {
			return position < 0 ? coord : Coordinates.getOffsetCoordinate(
					getSegment().getCrs(), getSubsegment().getLinearGeom(),
					position, lateralOffset);
		} catch (Exception e) {
			throw new ModelInputException(
					"vehicle coordinate transformation failed!", e);
		}
	}

	@Override
	public final void refresh() throws ModelInputException {
		Coordinate newCoord = computeCoord();
		if (speed > 0)
			this.angle = Angle.toDegrees(Angle.angle(coord, newCoord));
		this.coord = newCoord;
		onRefresh();
	}

	abstract protected void onRefresh();

}
