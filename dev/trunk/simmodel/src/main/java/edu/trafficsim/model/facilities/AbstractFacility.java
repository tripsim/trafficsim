package edu.trafficsim.model.facilities;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.Facility;
import edu.trafficsim.model.core.Coord;

public abstract class AbstractFacility<T> extends AbstractLocation<T> implements Facility {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractFacility(Coord coord) {
		super(coord);
	}


}
