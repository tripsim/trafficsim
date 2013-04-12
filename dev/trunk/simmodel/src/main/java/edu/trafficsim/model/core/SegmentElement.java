package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface SegmentElement extends DataContainer {

	public double getStart();

	public double getEnd();

	public double getShift();

	public double getWidth();

	public double getLength();

	public Segment getSegment();
}
