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

import edu.trafficsim.model.Location;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class LinearEvent<T> extends AbstractEvent<T>
		{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	public final static class EventSegment extends AbstractSegment<EventSegment> {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//
//		public EventSegment() {
//			// TODO a way to represent event area
//		}
//		
//		@Override
//		public LineString getLinearGeom() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
//
//	private EventSegment segment = new EventSegment();
	
	public LinearEvent(long id, String name, double startTime, double endTime, Location fromLocation, Location toLocation) {
		super(id, name, startTime, endTime);
	}

}
