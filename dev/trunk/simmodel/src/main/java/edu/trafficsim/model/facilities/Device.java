package edu.trafficsim.model.facilities;

import edu.trafficsim.model.core.Coord;

public abstract class Device<T> extends AbstractFacility<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public Device(Coord coord) {
		super(coord);
	}

}
