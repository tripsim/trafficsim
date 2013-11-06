package edu.trafficsim.model;


public class DriverType extends Type<DriverType> {

	private static final long serialVersionUID = 1L;

	public static enum Aggressiveness {
		moderate, aggressive,
	}

	private double minHeadway = 2;
	private double perceptionTime = 2;
	private double reactionTime = 2;

	//
	// private Aggressiveness drivingAggressiveness;
	// private Aggressiveness routeAggressiveness;

	public DriverType(long id, String name) {
		super(id, name);
	}

	public double getMinHeadway() {
		return minHeadway;
	}

	public void setMinHeadway(double minHeadway) {
		this.minHeadway = minHeadway;
	}

}
