package edu.trafficsim.engine.simulation;

import java.util.List;

public interface SimulationManager {

	SimulationSettings getDefaultSimulationSettings();

	String insertSimulation(String simulationName, String networkName,
			String odMatrixName, SimulationSettings settings);

	List<String> getSimulationNames(String networkName);

	ExecutedSimulation findSimulation(String name);

	ExecutedSimulation findLatestSimulation(String networkName);
}