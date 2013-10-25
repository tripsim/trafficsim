package edu.trafficsim.model.simulator;

import java.util.Date;
import java.util.Random;

import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractSimulator<T> extends BaseEntity<T> implements
		Simulator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date startTime;
	private double warmup; // in seconds

	protected double duration; // in seconds
	protected double stepSize; // in seconds

	private long seed;
	private Random rand;

	private double ttl;

	public AbstractSimulator(long id, String name, double duration,
			double stepSize, long seed) {
		super(id, name);
		this.duration = duration;
		this.stepSize = stepSize;
		this.seed = seed;

		this.warmup = 0;
		ready();
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public double getWarmup() {
		return warmup;
	}

	public void setWarmup(double warmup) {
		this.warmup = warmup;
	}

	@Override
	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	@Override
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

	@Override
	public Random getRand() {
		return rand;
	}

	@Override
	public double getForwarded() {
		return duration - stepSize * (double) ttl;
	}

	@Override
	public boolean isFinished() {
		return ttl < 0;
	}

	@Override
	public void stepForward() {
		ttl--;
	}

	private void ready() {
		ttl = (int) Math.round(duration / stepSize);
		rand = new Random(seed);
	}

}
