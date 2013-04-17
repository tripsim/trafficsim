package edu.trafficsim.model;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * @author Xuan
 * 
 */
public interface SubSegment extends DataContainer {

	public Coordinate getStartCoord();

	public Coordinate getEndCoord();

	public double getStart();

	public double getEnd();

	public double getShift();

	public double getWidth();

	public double getLength();

	public Segment getSegment();
}
