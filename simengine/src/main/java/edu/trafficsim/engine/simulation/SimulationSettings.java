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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class SimulationSettings {

	private int duration; // in seconds
	private double stepSize; // in seconds
	private int warmup; // in seconds
	private long seed;
	private double sd; // standard deviation

	String simulatingType;
	String vehicleGeneratingType;
	// key is vehicle type
	final Map<String, String> movingTypes = new HashMap<String, String>();
	final Map<String, String> carFollowingTypes = new HashMap<String, String>();
	final Map<String, String> laneChangingTypes = new HashMap<String, String>();
	final Map<String, String> routingTypes = new HashMap<String, String>();
	final Map<String, String> vehicleImpls = new HashMap<String, String>();
	final Map<String, String> driverImpls = new HashMap<String, String>();

	public SimulationSettings(int duration, double stepSize) {
		this.duration = duration;
		this.stepSize = stepSize;
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

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public double getSd() {
		return sd;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	public final String getSimulatingType() {
		return simulatingType;
	}

	public final void setSimulatingType(String simulatingType) {
		this.simulatingType = simulatingType;
	}

	public final String getVehicleGeneratingType() {
		return vehicleGeneratingType;
	}

	public final void setVehicleGeneratingType(String vehicleGeneratingType) {
		this.vehicleGeneratingType = vehicleGeneratingType;
	}

	public final String getMovingType(String vehicleType) {
		return movingTypes.get(vehicleType);
	}

	public final void setMovingType(String vehicleType, String movingType) {
		movingTypes.put(vehicleType, movingType);
	}

	public final String getRoutingType(String vehicleType) {
		return routingTypes.get(vehicleType);
	}

	public final void setRoutingType(String vehicleType, String routingType) {
		routingTypes.put(vehicleType, routingType);
	}

	public final String getCarFollowingType(String vehicleType) {
		return carFollowingTypes.get(vehicleType);
	}

	public final void setCarFollowingType(String vehicleType,
			String carFollowingType) {
		carFollowingTypes.put(vehicleType, carFollowingType);
	}

	public final String getLaneChangingType(String vehicleType) {
		return laneChangingTypes.get(vehicleType);
	}

	public final void setLaneChangingType(String vehicleType,
			String langeChangingType) {
		laneChangingTypes.put(vehicleType, langeChangingType);
	}

	public final String getVehicleImplType(String vehicleType) {
		return vehicleImpls.get(vehicleType);
	}

	public final void setVehicleImplType(String vehicleType,
			String vehicleImplType) {
		vehicleImpls.put(vehicleType, vehicleImplType);
	}

	public final String getDriverImplType(String vehicleType) {
		return driverImpls.get(vehicleType);
	}

	public final void setDriverImplType(String vehicleType, String driverImpl) {
		driverImpls.put(vehicleType, driverImpl);
	}
}
