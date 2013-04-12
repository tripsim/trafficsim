package edu.trafficsim.model.network;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.DataContainer;

public interface Navigable extends DataContainer {

	public Coordinate getStartCoordinate();

	public Coordinate getEndCoordinate();

	public LineString getLinearGeom();

	public Coordinate getCoordinate(double x, double y);
	
	public Path[] getPaths();

}
