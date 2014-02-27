package edu.trafficsim.plugin;

public interface IVehicle extends IPlugin {

	public double getMaxAccel(double speed, double maxSpeed);

	public double getMaxDecel(double speed);

}
