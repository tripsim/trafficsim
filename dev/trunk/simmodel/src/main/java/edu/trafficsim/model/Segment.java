package edu.trafficsim.model;

import java.util.List;

import com.vividsolutions.jts.geom.LineString;

/**
 * @author Xuan
 * 
 */
public interface Segment extends GeoReferenced {

	public Long getId();

	public String getName();

	public Location getStartLocation();

	public Location getEndLocation();

	public LineString getLinearGeom();

	public double getWidth();

	/**
	 * @return the real world length of the segment
	 */
	public double getLength();

	/**
	 * @return the linear geometry length, based on coordinates
	 */
	public double getGeomLength();

	public List<Subsegment> getSubsegments();

	public int sizeOfSubsegments();

}
