package edu.trafficsim.model.events;

import edu.trafficsim.model.Simulator;
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

		public EventAgent(int startFrame) {
			super(startFrame);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void before() {
			// TODO Auto-generated method stub

		}

		@Override
		protected void after(Simulator simulator) {
			// TODO Auto-generated method stub

		}

		@Override
		protected void update() {
			// TODO Auto-generated method stub

		}

	}

	private EventAgent agent;

	public ContinuousEvent(double startTime, double endTime, int startFrame) {
		super(startTime, endTime);
		agent = new EventAgent(startFrame);
	}

}
