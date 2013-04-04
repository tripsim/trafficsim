package edu.trafficsim.model.roadusers;

import edu.trafficsim.plugin.IPedestrian;

public class Pedestrian extends RoadUser<Pedestrian> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	IPedestrian impl;
	
	public Pedestrian(double startTime, double stepSize) {
		super(startTime, stepSize);
	}

	@Override
	public void stepForward(double stepSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
