package edu.trafficsim.model.roadusers;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.DataContainer;

public class Trajectory implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final short DEFAULT_RESOLUTION = 1;
	
	private List<Coordinate> coords;
	private short resolution;

	public Trajectory(Coordinate coord) {
		this(coord, DEFAULT_RESOLUTION);
	}
	
	public Trajectory(Coordinate coord, short resolution) {
		coords = new ArrayList<Coordinate>();
		coords.add(coord);
		this.resolution = resolution;
	}
	
	public short getResolution() {
		return resolution;
	}
	
	public Coordinate[] getCoords() {
		return coords.toArray(new Coordinate[coords.size()]);
	}
	
	public Coordinate[] getCoords(int resolution) {
		// TODO: implement the return method
		return null; 
	}
	
	public Coordinate getLastCoord() {
		return coords.get(coords.size() - 1);
	}
	
	public void add(Coordinate coord) {
		coords.add(coord);
	}
	

}
