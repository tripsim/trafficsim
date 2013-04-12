package edu.trafficsim.model.events;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;

public abstract class PointEvent<T> extends AbstractEvent<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class EventLocation extends AbstractLocation<EventLocation> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public EventLocation(Point point) {
			super(point);
		}

	}

	private EventLocation location;

	public PointEvent(double startTime, double endTime, Point point) {
		super(startTime, endTime);
		location = new EventLocation(point);
	}

}
