package edu.trafficsim.model.facilities;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.Facility;

public abstract class AbstractFacility<T> extends AbstractLocation<T> implements
		Facility {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractFacility(long id, String name, Point point) {
		super(id, name, point);
	}

}
