package edu.trafficsim.model.events;

import edu.trafficsim.model.Agent;
import edu.trafficsim.model.core.Event;
import edu.trafficsim.model.core.ImpactingObject;

public abstract class AbstractEvent<T> extends ImpactingObject<T> implements
		Agent, Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double startTime;
	private double endTime;

	public AbstractEvent(double startTime, double endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public double getStartTime() {
		return startTime;
	}

	public double getEndTime() {
		return endTime;
	}
}
