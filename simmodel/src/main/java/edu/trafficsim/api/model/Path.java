package edu.trafficsim.api.model;

import java.util.Collection;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.api.Environment;

public interface Path extends GeoReferenced, Environment {

	LineString getLinearGeom();

	Collection<? extends Path> getExits();
}
