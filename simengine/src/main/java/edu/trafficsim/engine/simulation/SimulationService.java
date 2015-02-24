package edu.trafficsim.engine.simulation;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;

public interface SimulationService {

	boolean isRunning(String simulationName);

	void execute(String simulationName, Network network, OdMatrix odMatrix);

	void execute(String simulationName, Network network, OdMatrix odMatrix,
			SimulationSettings settings);
}
