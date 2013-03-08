package edu.trafficsim.model.roadusers;

import edu.trafficsim.plugin.IPedestrian;

public class Pedestrian extends RoadUser<Pedestrian> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	IPedestrian impl;
	
	public Pedestrian(double trajectoryResolution) {
		super(trajectoryResolution);
	}

	@Override
	public void stepForward() {
		// TODO Auto-generated method stub
		
	}

}
