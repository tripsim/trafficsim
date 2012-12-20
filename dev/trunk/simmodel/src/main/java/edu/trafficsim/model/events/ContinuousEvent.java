package edu.trafficsim.model.events;

public abstract class ContinuousEvent extends Event<ContinuousEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ContinuousEvent(EventType eventType) {
		super(eventType);
	}

}
