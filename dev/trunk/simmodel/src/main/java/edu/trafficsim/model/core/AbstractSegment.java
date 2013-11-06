package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Segment;

public abstract class AbstractSegment<T> extends BaseEntity<T> implements
		Segment {

	private static final long serialVersionUID = 1L;

	// TODO use edge representation to fit in the map exactly, or other ways of
	// polygon representation
	// private LineString leftEdge;
	// private LineString rightEdge;

	public AbstractSegment(long id, String name) {
		super(id, name);
	}

	@Override
	public final Coordinate getStartCoordinate() {
		return getLinearGeom().getCoordinate();
	}

	@Override
	public final Coordinate getEndCoordinate() {
		return getLinearGeom().getCoordinateN(
				getLinearGeom().getNumPoints() - 1);
	}

	@Override
	public final Coordinate getCoordinate(double x, double y) {
		return Coordinates.transformFromLocal(getLinearGeom(), x, y);
	}

	@Override
	public double getAngle(double x) {
		return Coordinates.angleDegrees(getLinearGeom(), x);
	}

	@Override
	public double getLength() {
		return getLinearGeom().getLength();
	}

}
