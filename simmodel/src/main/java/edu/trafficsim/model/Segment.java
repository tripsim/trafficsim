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
package edu.trafficsim.model;

import java.util.List;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.core.ModelInputException;

/**
 * @author Xuan
 * 
 */
public interface Segment extends GeoReferenced {

	public Long getId();

	public void setId(Long id);

	Location getStartLocation();

	Location getEndLocation();

	LineString getLinearGeom();

	void setLinearGeom(Location startLocation, Location endLocation,
			LineString linearGeom) throws TransformException,
			ModelInputException;

	double getWidth();

	/**
	 * @return the real world length of the segment
	 */
	double getLength();

	/**
	 * @return the linear geometry length, based on coordinates
	 */
	double getGeomLength();

	List<Subsegment> getSubsegments();

	int sizeOfSubsegments();

}
