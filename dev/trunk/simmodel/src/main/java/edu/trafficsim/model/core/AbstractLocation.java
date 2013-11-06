package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Location;

public abstract class AbstractLocation<T> extends BaseEntity<T> implements
		Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Point point;
	private double radius;

	public AbstractLocation(long id, String name, Point point) {
		this(id, name, point, 0);
	}

	public AbstractLocation(long id, String name, Point point, double radius) {
		super(id, name);
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

}
