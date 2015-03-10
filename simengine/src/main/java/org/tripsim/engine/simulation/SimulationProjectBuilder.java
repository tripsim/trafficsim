package org.tripsim.engine.simulation;

import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;

public class SimulationProjectBuilder {

	public static final long DEFAULT_INIT_SEQ = 1000000000;

	String simulationName;
	Network network;
	OdMatrix odMatrix;
	SimulationSettings settings = null;
	long nextSeq = DEFAULT_INIT_SEQ;

	public SimulationProjectBuilder(String simulationName) {
		this.simulationName = simulationName;
	}

	public SimulationProjectBuilder() {
		this("");
	}

	public SimulationProjectBuilder withNetwork(Network network) {
		this.network = network;
		return this;

	}

	public SimulationProjectBuilder withOdMatrix(OdMatrix odMatrix) {
		this.odMatrix = odMatrix;
		return this;
	}

	public SimulationProjectBuilder withSettings(SimulationSettings settings) {
		this.settings = settings;
		return this;
	}

	public SimulationProjectBuilder withSeq(long seq) {
		this.nextSeq = seq;
		return this;
	}

	public SimulationProject build() {
		SimulationProject project = new SimulationProject(simulationName,
				network, odMatrix);
		project.setSettings(settings);
		project.setNextSeq(nextSeq);
		return project;
	}
}
