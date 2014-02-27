package edu.trafficsim.plugin.core;

public class PipesCarFollowing extends AbstractCarFollowingImpl {

	private static final long serialVersionUID = 1L;

	private double baseSpeed = 4.4704d;

	@Override
	protected double calculateAccel(double spacing, double reactionTime,
			double length, double speed, double desiredSpeed, double maxAccel,
			double maxDecel, double desiredAccel, double desiredDecel,
			double leadLength, double leadSpeed, double stepSize) {

		// double safeSpeed = ((spacing - leadLength) / length) * baseSpeed;
		// safeSpeed = safeSpeed > desiredSpeed ? desiredSpeed : safeSpeed;
		// double accel = safeSpeed - speed;

		double safeSpacing = (speed / baseSpeed) * length + leadLength;

		// make sure it is within stoppable distance
		double accel = ((spacing - safeSpacing) / stepSize - speed)
				/ stepSize;
		accel = accel > maxAccel ? maxAccel : accel < maxDecel ? maxDecel
				: accel;

		return accel;
	}

}
