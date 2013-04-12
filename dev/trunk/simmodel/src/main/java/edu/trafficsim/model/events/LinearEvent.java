package edu.trafficsim.model.events;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;

import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.Event;
import edu.trafficsim.model.core.Location;
import edu.trafficsim.model.network.Lane;

public abstract class LinearEvent<T> extends AbstractEvent<T>
		{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static class EventSegment extends AbstractSegment<EventSegment> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public EventSegment() {
			// TODO a way to represent event area
		}
		
		@Override
		public LineString getLinearGeom() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	private EventSegment segment;
	
	public LinearEvent(double startTime, double endTime, Location fromLocation, Location toLocation) {
		super(startTime, endTime);
		this.segment = new EventSegment();
	}

}
