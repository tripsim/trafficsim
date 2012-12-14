package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.Coord;
import edu.trafficsim.plugin.IPedestrian;

public class Pedestrian extends RoadUser<Pedestrian> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	IPedestrian impl;
	
	public Pedestrian(Coord coord) {
		super(coord);
	}

}
