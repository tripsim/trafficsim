package edu.trafficsim.model.events;

import edu.trafficsim.model.Segment;
import edu.trafficsim.model.SubSegment;
import edu.trafficsim.model.core.MovingObject;

public abstract class ContinuousEvent<T> extends AbstractEvent<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static class EventAgent extends MovingObject<EventAgent> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public EventAgent(long id, String name, int startFrame) {
			super(id, name, startFrame);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Segment getSegment() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SubSegment getSubSegment() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onRefresh() {
			// TODO Auto-generated method stub
			
		}



	}

	private EventAgent agent;

	public ContinuousEvent(long id, String name, double startTime,
			double endTime, int startFrame) {
		super(id, name, startTime, endTime);
		// reoragnize
		//agent = new EventAgent(startFrame);
	}

}
