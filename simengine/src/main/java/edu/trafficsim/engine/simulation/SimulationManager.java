package edu.trafficsim.engine.simulation;

public interface SimulationManager {

	SimulationSettings getDefaultSimulationSettings();

	void insertSimulation(String outcomeName, String networkName,
			String odMatrixName, SimulationSettings settings);
}