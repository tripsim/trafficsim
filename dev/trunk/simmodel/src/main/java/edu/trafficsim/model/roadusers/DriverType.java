package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.Type;

public class DriverType extends Type<DriverType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static enum Aggressiveness {
		moderate,
		aggressive,
	}

	private double minHeadway;
	private double perceptionTime;
	private double reactionTime;
//	
//	private Aggressiveness drivingAggressiveness;
//	private Aggressiveness routeAggressiveness;
	
	public DriverType() {
	}
	
	public double getMinHeadway(double minHeadway) {
		return minHeadway;
	}
	
	public void setMinHeadway(double minHeadway) {
		this.minHeadway = minHeadway;
	}
	
}
