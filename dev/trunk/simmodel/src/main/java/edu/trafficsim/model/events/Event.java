package edu.trafficsim.model.events;

import edu.trafficsim.model.core.BaseEntity;

public abstract class Event<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	EventType eventType;
	
	public Event(EventType eventType) {
		this.eventType = eventType;
	}
	
	public EventType getEventType() {
		return eventType;
	}

}
