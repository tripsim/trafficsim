package edu.trafficsim.model;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

public interface GeoReferenced {

	public CoordinateReferenceSystem getCrs();

	public void onGeomUpdated() throws TransformException;
}
