package edu.trafficsim.model;

import java.util.Date;

import edu.trafficsim.model.core.Rand;

public interface Simulator extends DataContainer {

	public Date getStartTime();

	public int getWarmup();

	public int getDuration();

	public double getStepSize();

	public int getTotalSteps();

	public long getSeed();

	public Rand getRand();

	public double getForwardedTime();

	public int getForwardedSteps();

	public boolean isFinished();

	public void stepForward();

	public void reset();
	
	public SimulatorType getSimulatorType();

}
