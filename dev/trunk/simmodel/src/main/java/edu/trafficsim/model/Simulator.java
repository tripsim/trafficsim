package edu.trafficsim.model;

import java.util.Date;
import java.util.Random;

public interface Simulator extends DataContainer {

	public Date getStartTime();

	public int getWarmup();

	public int getDuration();

	public double getStepSize();

	public int getTotalSteps();

	public long getSeed();

	public Random getRand();

	public double getForwardedTime();
	
	public int getForwardedSteps();

	public boolean isFinished();

	public void stepForward();

}
