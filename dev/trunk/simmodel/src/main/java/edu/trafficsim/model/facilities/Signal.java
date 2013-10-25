package edu.trafficsim.model.facilities;

import com.vividsolutions.jts.geom.Point;

public class Signal extends TCD<Signal> {

	private static final long serialVersionUID = 1L;

	public Signal(long id, String name, Point point) {
		super(id, name, point);
	}
}
