package edu.trafficsim.plugin.core;

import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IDriver;

public class DriverImpl extends AbstractPlugin implements IDriver {

	private static final long serialVersionUID = 1L;

	@Override
	public double getDesiredAccel(double speed, double desiredSpeed) {

		return desiredSpeed - speed > 3.5 ? 3.5 : desiredSpeed - speed;
	}

	@Override
	public double getDesiredDecel(double speed) {

		return speed > 2.8 ? -2.8 : -speed;
	}

}
