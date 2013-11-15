package edu.trafficsim.model;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

public interface Location extends GeoReferenced {

	public Long getId();

	public String getName();

	public void setName(String name);

	public Point getPoint();

	public double getRadius();

	public void setRadius(double radius) throws TransformException;

}