package edu.trafficsim.engine.simulation;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;

public interface SimulationService {

	void execute(Network network, OdMatrix odMatrix, SimulationSettings settings);
}
