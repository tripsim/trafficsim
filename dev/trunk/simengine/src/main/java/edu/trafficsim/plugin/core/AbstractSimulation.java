package edu.trafficsim.plugin.core;

import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.plugin.ISimulation;

public abstract class AbstractSimulation implements ISimulation {

	protected SimulationScenario simulationScenario;

	public AbstractSimulation(SimulationScenario simulationScenario) {
		this.simulationScenario = simulationScenario;
	}

}
