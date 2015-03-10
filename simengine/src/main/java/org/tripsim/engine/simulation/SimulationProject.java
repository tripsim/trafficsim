package org.tripsim.engine.simulation;

import java.io.Serializable;

import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;

public class SimulationProject implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final long DEFAULT_INIT_SEQ = 1000000000;

	String simulationName;
	Network network;
	OdMatrix odMatrix;
	SimulationSettings settings;
	long nextSeq = DEFAULT_INIT_SEQ;

	SimulationProject(String simulationName, Network network, OdMatrix odMatrix) {
		this.simulationName = simulationName;
		this.network = network;
		this.odMatrix = odMatrix;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public Network getNetwork() {
		return network;
	}

	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	public SimulationSettings getSettings() {
		return settings;
	}

	void setSettings(SimulationSettings settings) {
		this.settings = settings;
	}

	public long getNextSeq() {
		return nextSeq;
	}

	void setNextSeq(long nextSeq) {
		this.nextSeq = nextSeq;
	}

	public boolean isReady() {
		return network != null && odMatrix != null && settings != null
				&& odMatrix.getNetworkName() != null
				&& network.getName() != null
				&& odMatrix.getNetworkName() == network.getName();
	}

}
