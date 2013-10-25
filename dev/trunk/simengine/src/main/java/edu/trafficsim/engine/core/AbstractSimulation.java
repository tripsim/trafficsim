package edu.trafficsim.engine.core;

import edu.trafficsim.engine.Simulation;
import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Simulator;

public abstract class AbstractSimulation implements Simulation {

	protected Simulator simulator;
	protected Network network;
	protected SimulationScenario simulationScenario;

	public AbstractSimulation(Simulator simulator, Network network,
			SimulationScenario simulationScenario) {
		this.network = network;

		this.simulator = simulator;
		this.simulationScenario = simulationScenario;
	}

}
