/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.utility;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class Timer implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_DURATION = 100;
	private static final double DEFAULT_STEPSIZE = 0.5d;
	private static final long DEFAULT_SEED = 0l;

	private Date startTime;
	private int warmup; // in seconds

	protected int duration; // in seconds
	protected double stepSize; // in seconds

	private long seed;
	private Rand rand;

	private int forwardedSteps;

	public static Timer create() {
		return create(DEFAULT_DURATION, DEFAULT_STEPSIZE, DEFAULT_SEED);
	}

	public static Timer create(int duration, double stepSize, long seed) {
		return create(duration, 0, stepSize, seed);
	}

	public static Timer create(int duration, int warmup, double stepSize,
			long seed) {
		return new Timer(duration, stepSize, seed);
	}

	Timer(int duration, double stepSize, long seed) {
		this(duration, 0, stepSize, seed);
	}

	Timer(int duration, int warmup, double stepSize, long seed) {
		this.duration = duration;
		this.stepSize = stepSize;
		this.seed = seed;
		reset();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getWarmup() {
		return warmup;
	}

	public void setWarmup(int warmup) {
		this.warmup = warmup;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}

	public int getTotalSteps() {
		return (int) Math.round(duration / stepSize);
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public Rand getRand() {
		return rand;
	}

	public double getForwardedTime() {
		return stepSize * (double) forwardedSteps;
	}

	public int getForwardedSteps() {
		return forwardedSteps;
	}

	public boolean isFinished() {
		return duration < forwardedSteps * stepSize;
	}

	public void stepForward() {
		forwardedSteps++;
	}

	public void reset() {
		forwardedSteps = 0;
		rand = new Rand(seed);
	}

}
