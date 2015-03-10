package org.tripsim.plugin.core;

import org.springframework.stereotype.Component;
import org.tripsim.plugin.api.IMoving;

@Component("Simple Micro Scopic Moving")
public class SimpleMicroScopicMoving extends AbstractMicroScopicMoving
		implements IMoving {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Simple Micro Scopic Moving Kinetics";
	}

	@Override
	protected double deltaPosition(double direction, double speed,
			double stepSize) {
		return stepSize * speed;
	}

	@Override
	protected double deltaLateralOffset(double direction, double speed,
			double stepSize) {
		return 0;
	}

	@Override
	protected double deltaSpeed(double accel, double stepSize) {
		return stepSize * accel;
	}

}
