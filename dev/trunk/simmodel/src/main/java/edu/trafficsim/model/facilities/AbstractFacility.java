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
package edu.trafficsim.model.facilities;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.Facility;
import edu.trafficsim.model.core.AbstractLocation;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractFacility<T> extends AbstractLocation<T> implements
		Facility {

	private static final long serialVersionUID = 1L;

	public AbstractFacility(long id, String name, Point point) {
		super(id, name, point);
	}

	public void onGeomUpdated() throws TransformException {

	}
}
