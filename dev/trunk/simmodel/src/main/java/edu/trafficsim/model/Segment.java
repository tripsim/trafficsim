package edu.trafficsim.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

/**
 * @author Xuan
 * 
 */
public interface Segment extends DataContainer {

	public Long getId();

	public String getName();

	public Coordinate getStartCoordinate();

	public Coordinate getEndCoordinate();

	public LineString getLinearGeom();

	/**
	 * transfer local coordinate to global coordinate
	 * 
	 * @param x
	 *            position on the segment
	 * @param y
	 *            lateral offset
	 * @return
	 */
	public Coordinate getCoordinate(double x, double y);

	/**
	 * get the angle of the segment
	 * 
	 * @param x
	 *            position on the segment
	 * @return
	 */
	public double getAngle(double x);

	public double getWidth();

	public double getLength();

	// public List<SubSegment> getSubSegments();

}
