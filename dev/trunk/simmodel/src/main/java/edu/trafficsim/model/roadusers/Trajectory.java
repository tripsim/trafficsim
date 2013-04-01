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
	
	// start time, and step size, in seconds
	private final double startTime;
	private final double stepSize;
	
	public Trajectory(double startTime, double stepSize) {
		coords = new ArrayList<Coordinate>();
		
		this.startTime = startTime;
		this.stepSize = stepSize;
	}
	
	public double getStartTime() {
		return startTime;
	}
	
	public double getStepSize() {
		return stepSize;
	}
	
	public Coordinate[] getCoords() {
		return coords.toArray(new Coordinate[coords.size()]);
	}
	
	public Coordinate[] getCoords(int stepSize) {
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
