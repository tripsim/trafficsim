package org.tripsim.engine.simulation;

import java.util.Date;

public class ExecutedSimulation {

	Date timestamp;
	String name;
	String networkName;
	String odMatrixName;

	SimulationSettings settings;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getOdMatrixName() {
		return odMatrixName;
	}

	public void setOdMatrixName(String odMatrixName) {
		this.odMatrixName = odMatrixName;
	}

	public SimulationSettings getSettings() {
		return settings;
	}

	public void setSettings(SimulationSettings settings) {
		this.settings = settings;
	}

	public long getTotalFrames() {
		return Math.round(settings.getDuration() / settings.getStepSize());
	}

	public long getDuration() {
		return settings.getDuration();
	}

	public double getStepSize() {
		return settings.getStepSize();
	}
}
