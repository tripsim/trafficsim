package edu.trafficsim.model.events;

import edu.trafficsim.model.Location;

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
