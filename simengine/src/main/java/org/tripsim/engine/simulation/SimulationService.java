package org.tripsim.engine.simulation;

import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;

public interface SimulationService {

	boolean isRunning(String simulationName);

	void execute(String simulationName, Network network, OdMatrix odMatrix);

	void execute(String simulationName, Network network, OdMatrix odMatrix,
			SimulationSettings settings);
}
