package edu.trafficsim.engine.io;

import java.io.Serializable;

import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;

public class SimulationProject implements Serializable {

	private static final long serialVersionUID = 1L;

	Network network;
	OdMatrix odMatrix;
	SimulationSettings settings;

	public SimulationProject(Network network, OdMatrix odMatrix,
			SimulationSettings settings) {
		this.network = network;
		this.odMatrix = odMatrix;
		this.settings = settings;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	public void setOdMatrix(OdMatrix odMatrix) {
		this.odMatrix = odMatrix;
	}

	public SimulationSettings getSettings() {
		return settings;
	}

	public void setSettings(SimulationSettings settings) {
		this.settings = settings;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
