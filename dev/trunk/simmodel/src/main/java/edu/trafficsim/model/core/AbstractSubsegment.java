package edu.trafficsim.model.core;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Subsegment;

public abstract class AbstractSubsegment<T> extends BaseEntity<T> implements
		Subsegment {

	private static final long serialVersionUID = 1L;

	// the distance in the link where the lane starts and ends
	// 0.0 as the start , and 1.0 as the end of segment
	protected double start;
	protected double end;

	// in meter
	protected double width;
	protected double length;
	// shift from the center line of link
	protected double shift;

	protected LineString linearGeom;
	protected final Segment segment;

	public AbstractSubsegment(long id, String name, Segment segment,
			double start, double end, double width, double shift)
			throws ModelInputException, TransformException {

		super(id, name);
		this.segment = segment;
		this.width = width;
		this.shift = shift;

		if (start < 0 || start > 1 || end < 0 || end > 1)
			throw new ModelInputException(
					"Segment element exceeds its containing segment.");
		if (end < start)
			throw new ModelInputException("Segment element has negative length");
		this.start = start;
		this.end = end;
	}

	@Override
	public Segment getSegment() {
		return segment;
	}

	@Override
	public CoordinateReferenceSystem getCrs() {
		return segment == null ? null : segment.getCrs();
	}

	@Override
	public LineString getLinearGeom() {
		return linearGeom;
	}

	@Override
	public final double getStart() {
		return start;
	}

	@Override
	public final void setStart(double start) throws ModelInputException,
			TransformException {
		if (this.start == start)
			return;
		if (start < 0 || start > 1)
			throw new ModelInputException(
					"Segment element start should be within 0 ~ 1");
		this.start = start;
		onGeomUpdated();
	}

	@Override
	public final double getEnd() {
		return end;
	}

	@Override
	public final void setEnd(double end) throws ModelInputException,
			TransformException {
		if (this.end == end)
			return;
		if (end < 0 || end > 1)
			throw new ModelInputException(
					"Segment element end should be within 0 ~ 1");
		this.end = end;
		onGeomUpdated();
	}

	@Override
	public final double getShift() {
		return shift;
	}

	@Override
	public final void setShift(double shift) throws TransformException {
		if (this.shift == shift)
			return;
		this.shift = shift;
		onGeomUpdated();
	}

	@Override
	public final double getWidth() {
		return width;
	}

	@Override
	public final void setWidth(double width) throws TransformException {
		this.width = width;
		onGeomUpdated();
	}

	@Override
	public final double getLength() {
		return length;
	}

	@Override
	public void onGeomUpdated() throws TransformException {
		// TODO update linearGeom according to start, end, shift
		linearGeom = Coordinates.getOffSetLineString(getCrs(),
				segment.getLinearGeom(), shift);
		linearGeom = Coordinates.trimLinearGeom(getCrs(), linearGeom);
		length = Coordinates.orthodromicDistance(getCrs(), linearGeom);
	}
}
