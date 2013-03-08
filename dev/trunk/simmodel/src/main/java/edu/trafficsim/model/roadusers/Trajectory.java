package edu.trafficsim.model.roadusers;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.DataContainer;

public class Trajectory implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Coordinate> coords;
	private double resolution;
	
	public Trajectory(double resolution) {
		coords = new ArrayList<Coordinate>();
		this.resolution = resolution;
	}
	
	public double getResolution() {
		return resolution;
	}
	
	public Coordinate[] getCoords() {
		return coords.toArray(new Coordinate[coords.size()]);
	}
	
	public Coordinate[] getCoords(int resolution) {
		// TODO: implement the return method
		return null; 
	}
	
	public LineString toLineString() {
		// TODO implement
		return null;
	}
	
	public Coordinate getLastCoord() {
		return coords.get(coords.size() - 1);
	}
	
	public void add(Coordinate coord) {
		coords.add(coord);
	}
	

}
