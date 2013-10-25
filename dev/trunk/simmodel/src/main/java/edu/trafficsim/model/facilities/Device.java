package edu.trafficsim.model.facilities;

import com.vividsolutions.jts.geom.Point;

public abstract class Device<T> extends AbstractFacility<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Device(long id, String name, Point point) {
		super(id, name, point);
	}

}
