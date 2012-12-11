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
	
	private List<Position> positions;
	private short resolution;

	public Trajectory() {
		this(DEFAULT_RESOLUTION);
	}
	
	public Trajectory(short resolution) {
		positions = new ArrayList<Position>();
		this.resolution = resolution;
	}
	
	public short getResolution() {
		return resolution;
	}
	
	public Position[] getPositions() {
		return positions.toArray(new Position[positions.size()]);
	}
	
	public Position[] getPositions(int resolution) {
		// TODO: implement the return method
		return null; 
	}
	
	public Position getLastPosition() {
		return positions.get(positions.size() - 1);
	}
	
	public void add(Position position) {
		positions.add(position);
	}
	

}
