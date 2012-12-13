package edu.trafficsim.model.core;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.model.DataContainer;

public class Trajectory implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final short DEFAULT_RESOLUTION = 1;
	
	private List<Coord> coords;
	private short resolution;

	public Trajectory(Coord coord) {
		this(coord, DEFAULT_RESOLUTION);
	}
	
	public Trajectory(Coord coord, short resolution) {
		coords = new ArrayList<Coord>();
		coords.add(coord);
		this.resolution = resolution;
	}
	
	public short getResolution() {
		return resolution;
	}
	
	public Coord[] getCoords() {
		return coords.toArray(new Coord[coords.size()]);
	}
	
	public Coord[] getCoords(int resolution) {
		// TODO: implement the return method
		return null; 
	}
	
	public Coord getLastCoord() {
		return coords.get(coords.size() - 1);
	}
	
	public void add(Coord coord) {
		coords.add(coord);
	}
	

}
