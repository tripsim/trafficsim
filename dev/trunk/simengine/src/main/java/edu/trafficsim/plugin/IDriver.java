package edu.trafficsim.plugin;

public interface IDriver extends IPlugin {

	public double getDesiredAccel(double speed, double desiredSpeed);

	public double getDesiredDecel(double speed);
}
