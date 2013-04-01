package edu.trafficsim.model.simulator;

import java.util.Date;
import java.util.Random;

import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractSimulator<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date startTime;
	private double warmup; // in seconds
	
	protected double duration; // in seconds
	protected double stepSize; // in seconds
	
	private long seed;
	
	public AbstractSimulator (double duration, double stepSize, long seed) {
		this.warmup = 0;
		this.duration = duration;
		this.stepSize = stepSize;
		
		this.seed = seed;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public double getWarmup() {
		return warmup;
	}

	public void setWarmup(double warmup) {
		this.warmup = warmup;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}

	public Random getRand() {
		return new Random(seed);
	}
	
}
