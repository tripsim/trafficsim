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
package edu.trafficsim.engine.simulation;

import edu.trafficsim.util.Rand;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class Tracker {

	private final SimulationSettings settings;
	private final long timestamp;

	private Rand rand;
	private long forwardedSteps;
	private long vehicleCount;

	public Tracker(long timestamp, SimulationSettings settings) {
		this.timestamp = timestamp;
		this.settings = settings;
		forwardedSteps = 0;
		vehicleCount = 0;
		rand = new Rand(getSeed());
	}

	public int getWarmup() {
		return settings.getWarmup();
	}

	public int getDuration() {
		return settings.getDuration();
	}

	public double getStepSize() {
		return settings.getStepSize();
	}

	public long getSeed() {
		return settings.getSeed();
	}

	public double getSd() {
		return settings.getSd();
	}

	public final String getSimulatingType() {
		return settings.getSimulatingType();
	}

	public final String getVehicleGeneratingType() {
		return settings.getVehicleGeneratingType();
	}

	public final String getMovingType(String vehicleType) {
		return settings.getMovingType(vehicleType);
	}

	public final String getRoutingType(String vehicleType) {
		return settings.getRoutingType(vehicleType);
	}

	public final String getCarFollowingType(String vehicleType) {
		return settings.getCarFollowingType(vehicleType);
	}

	public final String getLaneChangingType(String vehicleType) {
		return settings.getLaneChangingType(vehicleType);
	}

	public final String getVehicleImplType(String vehicleType) {
		return settings.getVehicleImplType(vehicleType);
	}

	public final String getDriverImplType(String vehicleType) {
		return settings.getDriverImplType(vehicleType);
	}

	// States
	public long getTimestamp() {
		return timestamp;
	}

	public int getTotalSteps() {
		return (int) Math.round(getDuration() / getStepSize());
	}

	public Rand getRand() {
		return rand;
	}

	public double getForwardedTime() {
		return getStepSize() * (double) forwardedSteps;
	}

	public long getForwardedSteps() {
		return forwardedSteps;
	}

	public long getAndIncrementVehicleCount() {
		return vehicleCount++;
	}

	public boolean isFinished() {
		return getDuration() < forwardedSteps * getStepSize();
	}

	public void stepForward() {
		forwardedSteps++;
	}
}