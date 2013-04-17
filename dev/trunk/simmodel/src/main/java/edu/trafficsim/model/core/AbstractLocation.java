package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.Location;

public abstract class AbstractLocation<T> extends BaseEntity<T> implements
		Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Point point;
	private double radius;

	public AbstractLocation(Point point) {
		this(point, 0);
	}
	
	public AbstractLocation(Point point, double radius) {
		this.point = point;
		this.radius = radius;
	}


	@Override
	public final Point getPoint() {
		return point;
	}

	protected final void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public final double getRadius() {
		return radius;
	}

	protected final void setRadius(double radius) {
		this.radius = radius;
	}
	
	@Override
	public void transform(CoordinateFilter filter) {
		point.apply(filter);
	}
}
