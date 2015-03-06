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
package edu.trafficsim.util;

/**
 * 
 * 
 * @author Xuan Shi
 */
public final class Timer {

	private final double stepSize;
	private long totalSteps;
	private long forwardedSteps;

	public Timer(long duration, double stepSize) {
		this.stepSize = stepSize;
		totalSteps = Math.round(duration / stepSize);
		forwardedSteps = 0;
	}

	public long getTotalSteps() {
		return totalSteps;
	}

	public double getForwardedTime() {
		return stepSize * (double) forwardedSteps;
	}

	public long getForwardedSteps() {
		return forwardedSteps;
	}

	public boolean isFinished() {
		return totalSteps < forwardedSteps;
	}

	public void stepForward() {
		forwardedSteps++;
	}
}