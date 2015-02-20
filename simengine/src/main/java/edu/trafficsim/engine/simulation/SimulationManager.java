package edu.trafficsim.engine.simulation;

public interface SimulationManager {

	SimulationSettings getDefaultSimulationSettings();

	String insertSimulation(String outcomeName, String networkName,
			String odMatrixName, SimulationSettings settings);
}