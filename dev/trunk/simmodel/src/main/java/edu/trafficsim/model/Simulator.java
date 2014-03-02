package edu.trafficsim.model;

import java.util.Date;

import edu.trafficsim.model.core.Rand;

public interface Simulator extends DataContainer {

	SimulatorType getSimulatorType();

	void setSimulatorType(SimulatorType type);

	Date getStartTime();

	int getWarmup();

	void setWarmup(int warmup);

	int getDuration();

	void setDuration(int duration);

	double getStepSize();

	void setStepSize(double stepSize);

	long getSeed();

	void setSeed(long seed);

	int getTotalSteps();

	Rand getRand();

	double getForwardedTime();

	int getForwardedSteps();

	boolean isFinished();

	void stepForward();

	void reset();

}
