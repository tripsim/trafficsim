package edu.trafficsim.model.core;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Subsegment;
import edu.trafficsim.model.util.Coordinates;

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
			throws TransformException {

		super(id, name);
		this.segment = segment;
		this.start = start;
		this.end = end;
		this.width = width;
		this.shift = shift;

		fixkStartEnd(start, end);
	}

	private void fixkStartEnd(double start, double end) {
		try {
			checkStartEnd(start, end);
		} catch (ModelInputException e) {
			this.start = this.end = 0;
		}
	}

	private void checkStartEnd(double start, double end)
			throws ModelInputException {
		// if (start < 0 || end > 0)
		// throw new ModelInputException(
		// "Segment element exceeds its containing segment.");
		if (start - end > segment.getLength())
			throw new ModelInputException("Segment element has negative length");
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
		checkStartEnd(start, end);
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

		checkStartEnd(start, end);
		this.end = end;
		onGeomUpdated();
	}

	@Override
	public final double getShift() {
		return shift;
	}

	@Override
	public final void setShift(double shift, boolean update)
			throws TransformException {
		if (update) {
			onShiftUpdate(shift - this.shift);
		}
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
	public final void setWidth(double width, boolean update)
			throws TransformException {
		if (update) {
			onWidthUpdate(width - this.width);
		}
		if (this.width == width)
			return;
		this.width = width;
		onGeomUpdated();
	}

	@Override
	public final double getLength() {
		return length;
	}

	@Override
	public void onGeomUpdated() throws TransformException {
		linearGeom = Coordinates.getOffSetLineString(getCrs(),
				segment.getLinearGeom(), shift);
		fixkStartEnd(start, end);
		linearGeom = Coordinates.trimLinearGeom(getCrs(), linearGeom, start,
				-end);
		length = Coordinates.orthodromicDistance(getCrs(), linearGeom);
	}

	abstract protected void onShiftUpdate(double offset)
			throws TransformException;

	abstract protected void onWidthUpdate(double offset)
			throws TransformException;
}
