package edu.trafficsim.model;

import com.vividsolutions.jts.geom.LineString;

/**
 * @author Xuan
 * 
 */
public interface Subsegment extends GeoReferenced {

	public Long getId();

	public String getName();

	public double getStart();

	public double getEnd();

	public double getShift();

	public double getWidth();

	public double getLength();

	public LineString getLinearGeom();

	public Segment getSegment();

}
