package edu.trafficsim.model.facilities;

import com.vividsolutions.jts.geom.Point;

public class Detector extends Device<Detector> {

	private static final long serialVersionUID = 1L;

	public Detector(long id, String name, Point point) {
		super(id, name, point);
	}

}
