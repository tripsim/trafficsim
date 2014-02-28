package edu.trafficsim.plugin;

public interface IDriver extends IPlugin {

	double getDesiredAccel(double speed, double desiredSpeed);

	double getDesiredDecel(double speed);
}
