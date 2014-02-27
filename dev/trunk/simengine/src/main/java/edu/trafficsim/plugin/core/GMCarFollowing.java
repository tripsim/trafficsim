package edu.trafficsim.plugin.core;

public class GMCarFollowing extends AbstractCarFollowingImpl {

	private static final long serialVersionUID = 1L;

	public double alpha = 0.2;

	@Override
	protected double calculateAccel(double spacing, double reactionTime,
			double length, double speed, double desiredSpeed, double maxAccel,
			double maxDecel, double desiredAccel, double desiredDecel,
			double leadLength, double leadSpeed, double stepSize) {
		return alpha * (speed - leadSpeed);
	}

}
