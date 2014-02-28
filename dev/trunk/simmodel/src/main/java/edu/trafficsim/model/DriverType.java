package edu.trafficsim.model;


public class DriverType extends Type<DriverType> {

	private static final long serialVersionUID = 1L;

	public static enum Aggressiveness {
		moderate, aggressive,
	}

	double perceptionTime = 2;
	double reactionTime = 2;

	double desiredHeadway = 2;
	double desiredSpeed = 25;

	//
	// private Aggressiveness drivingAggressiveness;
	// private Aggressiveness routeAggressiveness;

	public DriverType(long id, String name) {
		super(id, name);
	}

	public double getPerceptionTime() {
		return perceptionTime;
	}

	public void setPerceptionTime(double perceptionTime) {
		this.perceptionTime = perceptionTime;
	}

	public double getReactionTime() {
		return reactionTime;
	}

	public void setReactionTime(double reactionTime) {
		this.reactionTime = reactionTime;
	}

	public double getDesiredHeadway() {
		return desiredHeadway;
	}

	public void setDesiredHeadway(double minHeadway) {
		this.desiredHeadway = minHeadway;
	}

	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	public void setDesiredSpeed(double desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

}
