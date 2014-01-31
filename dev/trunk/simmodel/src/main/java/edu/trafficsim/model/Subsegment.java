package edu.trafficsim.model;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.core.ModelInputException;

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

	void setStart(double start) throws ModelInputException, TransformException;

	void setEnd(double end) throws ModelInputException, TransformException;

	void setShift(double shift) throws TransformException;

	void setWidth(double width) throws TransformException;

}
