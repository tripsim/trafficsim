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
package org.tripsim.api.model;

import java.util.List;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

/**
 * @author Xuan
 * 
 */
public interface Arc extends GeoReferenced {

	Point getStartPoint();

	Point getEndPoint();

	LineString getLinearGeom();

	void setLinearGeom(LineString linearGeom);

	double getWidth();

	/**
	 * @return the real world length of the segment
	 */
	double getLength();

	/**
	 * @return the linear geometry length, based on coordinates
	 */
	double getGeomLength();

	List<? extends ArcSection> getSections();

}
