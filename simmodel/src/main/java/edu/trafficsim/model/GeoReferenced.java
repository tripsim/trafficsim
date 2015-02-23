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

import org.opengis.referencing.crs.CoordinateReferenceSystem;

import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface GeoReferenced extends ObjectContainer {

	CoordinateReferenceSystem getCrs();

	void onGeomUpdated() throws ModelInputException;

	void onTransformDone(CoordinateReferenceSystem crs);
}
