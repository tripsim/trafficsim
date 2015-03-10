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
package org.tripsim.model.core;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tripsim.api.model.Arc;
import org.tripsim.api.model.ArcSection;
import org.tripsim.model.BaseObject;
import org.tripsim.model.util.Coordinates;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractArc extends BaseObject implements Arc {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractArc.class);

	private CoordinateReferenceSystem crs = null;

	protected LineString linearGeom;
	protected double length;

	public AbstractArc(long id, LineString linearGeom) {
		super(id);
		checkLinearGeom(linearGeom);
		this.linearGeom = linearGeom;
		calculateLength();
	}

	AbstractArc(long id) {
		super(id);
	}

	private void checkLinearGeom(LineString linearGeom) {
		if (linearGeom == null) {
			throw new IllegalArgumentException(
					"linearGeom cannot be set to null");
		}
	}

	@Override
	public final CoordinateReferenceSystem getCrs() {
		return crs;
	}

	@Override
	public final LineString getLinearGeom() {
		return linearGeom;
	}

	@Override
	public final void setLinearGeom(LineString linearGeom) {
		checkLinearGeom(linearGeom);
		this.linearGeom = linearGeom;
		onGeomUpdated();
	}

	@Override
	public final Point getStartPoint() {
		return linearGeom == null ? null : linearGeom.getStartPoint();
	}

	@Override
	public final Point getEndPoint() {
		return linearGeom == null ? null : linearGeom.getEndPoint();
	}

	@Override
	public double getWidth() {
		double width = 0;
		for (ArcSection section : getSections()) {
			width += section.getWidth();
		}
		return width;
	}

	@Override
	public final double getLength() {
		return length;
	}

	@Override
	public final double getGeomLength() {
		return getLinearGeom().getLength();
	}

	protected void calculateLength() {
		try {
			length = Coordinates.orthodromicDistance(getCrs(), getLinearGeom());
		} catch (Exception e) {
			logger.warn("Cannot calculate legnth for the arc, due to {}", e);
			length = 0;
		}
	}

	@Override
	public void onGeomUpdated() {
		calculateLength();
		for (ArcSection section : getSections()) {
			section.onGeomUpdated();
		}
	}

	@Override
	public void onTransformDone(CoordinateReferenceSystem crs) {
		this.crs = crs;
	}

}
