package edu.trafficsim.engine.simulation;

import org.springframework.stereotype.Service;

@Service("default-simulation-manager")
public class DefaultSimulationManager implements SimulationManager {

	@Override
	public SimulationSettings getDefaultSimulationSettings() {
		return new SimulationSettingsBuilder().build();
	}

	@Override
	public void insertSimulation(String outcomeName, SimulationSettings settings) {
		// TODO Auto-generated method stub

	}

}
