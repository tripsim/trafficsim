package edu.trafficsim.model.simulator;

import java.util.Date;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.SimulatorType;
import edu.trafficsim.model.core.Rand;

public abstract class AbstractSimulator<T> extends BaseEntity<T> implements
		Simulator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SimulatorType type;

	private Date startTime;
	private int warmup; // in seconds

	protected int duration; // in seconds
	protected double stepSize; // in seconds

	private long seed;
	private Rand rand;

	private int forwardedSteps;

	public AbstractSimulator(long id, String name, SimulatorType type,
			int duration, double stepSize, long seed) {
		super(id, name);
		this.type = type;
		this.duration = duration;
		this.stepSize = stepSize;
		this.seed = seed;

		this.warmup = 0;
		reset();
	}

	@Override
	public SimulatorType getSimulatorType() {
		// TODO
		return type;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public int getWarmup() {
		return warmup;
	}

	public void setWarmup(int warmup) {
		this.warmup = warmup;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}

	@Override
	public int getTotalSteps() {
		return (int) Math.round(duration / stepSize);
	}

	@Override
	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	@Override
	public Rand getRand() {
		return rand;
	}

	@Override
	public double getForwardedTime() {
		return stepSize * (double) forwardedSteps;
	}

	@Override
	public int getForwardedSteps() {
		return forwardedSteps;
	}

	@Override
	public boolean isFinished() {
		return duration < forwardedSteps * stepSize;
	}

	@Override
	public void stepForward() {
		forwardedSteps++;
	}

	@Override
	public void reset() {
		forwardedSteps = 0;
		rand = new Rand(seed);
	}
}
