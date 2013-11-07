package edu.trafficsim.model;

import com.vividsolutions.jts.geom.Coordinate;

public interface Movable extends DataContainer {

	public double position();

	public void position(double position);

	public double speed();

	public void speed(double speed);

	public double acceleration();

	public void acceleration(double acceleration);

	public Coordinate coord();

	public double angle();

	public Segment getSegment();

	public Subsegment getSubsegment();

}