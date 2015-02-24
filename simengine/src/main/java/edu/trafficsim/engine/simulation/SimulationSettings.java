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

import edu.trafficsim.plugin.PluginManager;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class SimulationSettings {

	private long duration; // in seconds
	private double stepSize; // in seconds
	private long warmup; // in seconds
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

	public SimulationSettings(long duration, double stepSize) {
		this.duration = duration;
		this.stepSize = stepSize;
	}

	public long getWarmup() {
		return warmup;
	}

	public void setWarmup(long warmup) {
		this.warmup = warmup;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
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
		if (simulatingType == null) {
			setSimulatingType(simulatingType = PluginManager.DEFAULT_SIMULATING);
		}
		return simulatingType;
	}

	public final void setSimulatingType(String simulatingType) {
		this.simulatingType = simulatingType;
	}

	public final String getVehicleGeneratingType() {
		if (vehicleGeneratingType == null) {
			setVehicleGeneratingType(vehicleGeneratingType = PluginManager.DEFAULT_GENERATING);
		}
		return vehicleGeneratingType;
	}

	public final void setVehicleGeneratingType(String vehicleGeneratingType) {
		this.vehicleGeneratingType = vehicleGeneratingType;
	}

	public final String getMovingType(String vehicleType) {
		String type = movingTypes.get(vehicleType);
		if (type == null) {
			setMovingType(vehicleType, type = PluginManager.DEFAULT_MOVING);
		}
		return type;
	}

	public final void setMovingType(String vehicleType, String movingType) {
		movingTypes.put(vehicleType, movingType);
	}

	public final String getRoutingType(String vehicleType) {
		String type = routingTypes.get(vehicleType);
		if (type == null) {
			setRoutingType(vehicleType, type = PluginManager.DEFAULT_ROUTING);
		}
		return type;
	}

	public final void setRoutingType(String vehicleType, String routingType) {
		routingTypes.put(vehicleType, routingType);
	}

	public final String getCarFollowingType(String vehicleType) {
		String type = carFollowingTypes.get(vehicleType);
		if (type == null) {
			setCarFollowingType(vehicleType,
					type = PluginManager.DEFAULT_CAR_FOLLOWING);
		}
		return type;
	}

	public final void setCarFollowingType(String vehicleType,
			String carFollowingType) {
		carFollowingTypes.put(vehicleType, carFollowingType);
	}

	public final String getLaneChangingType(String vehicleType) {
		String type = laneChangingTypes.get(vehicleType);
		if (type == null) {
			setLaneChangingType(vehicleType,
					type = PluginManager.DEFAULT_LANE_CHANGING);
		}
		return type;
	}

	public final void setLaneChangingType(String vehicleType,
			String langeChangingType) {
		laneChangingTypes.put(vehicleType, langeChangingType);
	}

	public final String getVehicleImplType(String vehicleType) {
		String type = vehicleImpls.get(vehicleType);
		if (type == null) {
			setVehicleImplType(vehicleType,
					type = PluginManager.DEFAULT_VEHICLE_IMPL);
		}
		return type;
	}

	public final void setVehicleImplType(String vehicleType,
			String vehicleImplType) {
		vehicleImpls.put(vehicleType, vehicleImplType);
	}

	public final String getDriverImplType(String vehicleType) {
		String type = driverImpls.get(vehicleType);
		if (type == null) {
			setDriverImplType(vehicleType,
					type = PluginManager.DEFAULT_DRIVER_IMPL);
		}
		return type;
	}

	public final void setDriverImplType(String vehicleType, String driverImpl) {
		driverImpls.put(vehicleType, driverImpl);
	}
}
