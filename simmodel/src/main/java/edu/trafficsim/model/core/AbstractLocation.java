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

import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.api.model.Location;
import edu.trafficsim.model.BaseObject;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractLocation extends BaseObject implements Location {

	private static final long serialVersionUID = 1L;

	private CoordinateReferenceSystem crs = null;
	protected Point point;
	protected double radius;

	public AbstractLocation(long id, Point point) {
		this(id, point, 0);
	}

	public AbstractLocation(long id, Point point, double radius) {
		super(id);
		this.point = point;
		this.radius = radius;
	}

	@Override
	public CoordinateReferenceSystem getCrs() {
		return crs;
	}

	@Override
	public final Point getPoint() {
		return point;
	}

	protected final void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public final double getRadius() {
		return radius;
	}

	@Override
	public final void setRadius(double radius) {
		this.radius = radius;
		// TODO reconsider the necessity for radius
		onGeomUpdated();
	}

	@Override
	public void onTransformDone(CoordinateReferenceSystem crs) {
		this.crs = crs;
	}

	@Override
	public void onGeomUpdated() {
	}
}
