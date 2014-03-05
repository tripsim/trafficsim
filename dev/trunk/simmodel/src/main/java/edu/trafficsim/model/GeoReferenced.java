package edu.trafficsim.model;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

public interface GeoReferenced extends DataContainer {

	CoordinateReferenceSystem getCrs();

	void onGeomUpdated() throws TransformException;
}
