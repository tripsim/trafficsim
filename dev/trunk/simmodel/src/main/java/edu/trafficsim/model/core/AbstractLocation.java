package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Point;

public abstract class AbstractLocation<T> extends BaseEntity<T> implements Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Point point;
	
	public AbstractLocation() {
		this(null);
	}
	
	public AbstractLocation(Point point) {
		this.point = point;
	}

	@Override
	public Point getPoint() {
		return point;
	}
	
	@Override
	public void setPoint(Point point) {
		this.point = point;
	}

}
