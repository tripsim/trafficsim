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
package edu.trafficsim.model.events;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class PointEvent<T> extends AbstractEvent<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	public static class EventLocation extends AbstractLocation<EventLocation> {

		private static final long serialVersionUID = 1L;

		public EventLocation(long id, String name, Point point) {
			super(id, name, point);
		}

		@Override
		public void onGeomUpdated() throws ModelInputException {
		}

	}

	@SuppressWarnings("unused")
	private EventLocation location;

	public PointEvent(long id, String name, double startTime, double endTime,
			Point point) {
		super(id, name, startTime, endTime);
		// reorganize
		// location = new EventLocation(point);
	}

}
