package edu.trafficsim.model.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

public abstract class AbstractSegment<T> extends BaseEntity<T> implements
		Segment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO use edge representation to fit in the map exactly, or other ways of
	// polygon representation
	// private LineString leftEdge;
	// private LineString rightEdge;

	protected final List<SegmentElement> elements = new ArrayList<SegmentElement>();

	public AbstractSegment() {
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
	public List<SegmentElement> getElements() {
		return Collections.unmodifiableList(elements);
	}
	
	@Override
	public double getWidth() {
		double width = 0;
		for (SegmentElement element : elements)
			width += element.getWidth();
		return width;
	}

	@Override
	public double getLength() {
		return getLinearGeom().getLength();
	}

}
