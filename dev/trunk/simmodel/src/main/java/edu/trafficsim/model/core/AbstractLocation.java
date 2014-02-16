package edu.trafficsim.model.core;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Location;

public abstract class AbstractLocation<T> extends BaseEntity<T> implements
		Location {

	private static final long serialVersionUID = 1L;

	private CoordinateReferenceSystem crs = null;
	protected Point point;
	protected double radius;

	public AbstractLocation(long id, String name, Point point) {
		this(id, name, point, 0);
	}

	public AbstractLocation(long id, String name, Point point, double radius) {
		super(id, name);
		this.point = point;
		this.radius = radius;
	}

	@Override
	public CoordinateReferenceSystem getCrs() {
		return crs;
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

	@Override
	public final void setRadius(double radius) throws TransformException {
		this.radius = radius;
		// TODO reconsider the necessity for radius
		// onGeomUpdated();
	}

}
