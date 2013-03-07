package edu.trafficsim.model.simulator;

import java.util.Date;

import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractSimulator<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date startTime;
	
	private double warmup; // in seconds
	private double duration; // in seconds
	private double stepSize; // in seconds
	
	
	public AbstractSimulator (double duration, double stepSize) {
		this.duration = duration;
		this.stepSize = stepSize;
		this.warmup = 0;
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

	
}
