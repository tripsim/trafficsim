package edu.trafficsim.model.events;

public abstract class PointEvent extends Event<PointEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PointEvent(EventType eventType) {
		super(eventType);
	}
	
}
