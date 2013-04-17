package edu.trafficsim.model;

import java.util.Date;
import java.util.Random;


public interface Simulator extends DataContainer {

	public Date getStartTime();

	public double getWarmup();

	public double getDuration();

	public double getStepSize();
	
	public long getSeed();

	public Random getRand();

	public double getForwarded();
	
	public boolean isFinished();
	
	public void stepForward();

}
