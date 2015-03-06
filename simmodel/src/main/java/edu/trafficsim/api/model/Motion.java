package edu.trafficsim.api.model;

public interface Motion {

	double getPosition();

	void setPosition(double position);

	double getLateralOffset();

	void setLateralOffset(double lateralOffset);

	double getDirection();

	void setDirection(double direction);

	double getSpeed();

	void setSpeed(double speed);

	double getAcceleration();

	void setAcceleration(double acceleration);
	
	void update(Motion motion);
}
