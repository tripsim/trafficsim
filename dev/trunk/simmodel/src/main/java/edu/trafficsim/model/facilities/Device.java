package edu.trafficsim.model.facilities;

import edu.trafficsim.model.core.Position;

public abstract class Device<T> extends AbstractFacility<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public Device(Position position) {
		super(position);
	}

}
