package edu.trafficsim.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.LineString;

/**
 * @author Xuan
 * 
 */
public interface Segment extends DataContainer {

	public Coordinate getStartCoordinate();

	public Coordinate getEndCoordinate();

	public LineString getLinearGeom();

	// transfer local coordinate to global coordinate
	public Coordinate getCoordinate(double x, double y);

	public double getWidth();

	public double getLength();

	// public List<SubSegment> getSubSegments();
	
	public void transform(CoordinateFilter filter);

}
