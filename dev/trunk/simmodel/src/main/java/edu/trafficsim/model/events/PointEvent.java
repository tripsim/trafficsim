package edu.trafficsim.model.events;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;

public abstract class PointEvent<T> extends AbstractLocation<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PointEvent(Point point) {
		super(point);
	}
	
	// TODO add timewindow to accomodate common features of events
}
