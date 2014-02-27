package edu.trafficsim.plugin.core;

import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IVehicle;

public class VehicleImpl extends AbstractPlugin implements IVehicle {

	private static final long serialVersionUID = 1L;

	@Override
	public double getMaxAccel(double speed, double maxSpeed) {

		return maxSpeed - speed > 3.5 ? 3.5 : maxSpeed - speed;
	}

	@Override
	public double getMaxDecel(double speed) {

		return speed > 2.8 ? -2.8 : -speed;
	}
}
