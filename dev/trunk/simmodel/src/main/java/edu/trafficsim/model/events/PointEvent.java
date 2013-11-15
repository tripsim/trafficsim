package edu.trafficsim.model.events;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;

public abstract class PointEvent<T> extends AbstractEvent<T> {

	private static final long serialVersionUID = 1L;

	public static class EventLocation extends AbstractLocation<EventLocation> {

		private static final long serialVersionUID = 1L;

		public EventLocation(long id, String name, Point point) {
			super(id, name, point);
		}

		@Override
		public void onGeomUpdated() throws TransformException {
			// TODO Auto-generated method stub

		}

	}

	private EventLocation location;

	public PointEvent(long id, String name, double startTime, double endTime,
			Point point) {
		super(id, name, startTime, endTime);
		// reorganize
		// location = new EventLocation(point);
	}

}
