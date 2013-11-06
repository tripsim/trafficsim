package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.SubSegment;

public abstract class AbstractSubSegment<T> extends BaseEntity<T> implements
		SubSegment {

	private static final long serialVersionUID = 1L;

	// the distance in the link where the lane starts and ends
	// 0.0 as the start , and 1.0 as the end of segment
	protected double start = 0;
	protected double end = 1;

	// in meter
	protected double width;

	// shift from the center line of link
	protected double shift;

	protected Segment segment;

	public AbstractSubSegment(long id, String name, Segment segment,
			double width, double shift) {
		super(id, name);
		this.segment = segment;
		this.width = width;
		this.shift = shift;
	}

	public AbstractSubSegment(long id, String name, Segment segment,
			double start, double end, double width, double shift)
			throws ModelInputException {
		this(id, name, segment, start, shift);

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

	public Coordinate getStartCoord() {
		return segment.getCoordinate(start, shift);
	}

	public Coordinate getEndCoord() {
		return segment.getCoordinate(end, shift);
	}

	@Override
	public double getStart() {
		return start;
	}

	public void setStart(double start) throws ModelInputException {
		if (start < 0 || start > 1)
			throw new ModelInputException(
					"Segment element start should be within 0 ~ 1");
		this.start = start;
	}

	@Override
	public double getEnd() {
		return end;
	}

	public void setEnd(double end) throws ModelInputException {
		if (end < 0 || end > 1)
			throw new ModelInputException(
					"Segment element end should be within 0 ~ 1");
		this.end = end;
	}

	@Override
	public double getShift() {
		return shift;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getLength() {
		return (end - start) * segment.getLength();
	}

}
