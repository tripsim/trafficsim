package edu.trafficsim.model.events;

import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.Location;

public abstract class LinearEvent<T> extends AbstractSegment<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public LinearEvent(Location fromLocation, Location toLocation) {
		super(fromLocation, toLocation);
	}
}
