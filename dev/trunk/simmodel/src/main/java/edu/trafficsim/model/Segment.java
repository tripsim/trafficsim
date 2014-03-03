package edu.trafficsim.model;

import java.util.List;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.core.ModelInputException;

/**
 * @author Xuan
 * 
 */
public interface Segment extends GeoReferenced {

	public Location getStartLocation();

	public Location getEndLocation();

	public LineString getLinearGeom();

	void setLinearGeom(Location startLocation, Location endLocation,
			LineString linearGeom) throws TransformException,
			ModelInputException;

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
