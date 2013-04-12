package edu.trafficsim.model.events;

import edu.trafficsim.model.core.MovingObject;
import edu.trafficsim.model.network.Path;

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

		public EventAgent(double startTime) {
			super(startTime);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void before() {
			// TODO Auto-generated method stub

		}

		@Override
		protected void after() {
			// TODO Auto-generated method stub

		}

		@Override
		protected void update() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean beyondEnd() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Path getPath() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private EventAgent agent;

	public ContinuousEvent(double startTime, double endTime) {
		super(startTime, endTime);
		agent = new EventAgent(startTime);
	}

}
