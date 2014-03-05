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

	Location getStartLocation();

	Location getEndLocation();

	LineString getLinearGeom();

	void setLinearGeom(Location startLocation, Location endLocation,
			LineString linearGeom) throws TransformException,
			ModelInputException;

	double getWidth();

	/**
	 * @return the real world length of the segment
	 */
	double getLength();

	/**
	 * @return the linear geometry length, based on coordinates
	 */
	double getGeomLength();

	List<Subsegment> getSubsegments();

	int sizeOfSubsegments();

}
