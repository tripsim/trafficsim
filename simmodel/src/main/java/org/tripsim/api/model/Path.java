package org.tripsim.api.model;

import java.util.Collection;

import org.tripsim.api.Environment;

import com.vividsolutions.jts.geom.LineString;

public interface Path extends GeoReferenced, Environment {

	LineString getLinearGeom();

	Collection<? extends Path> getExits();
}
