package edu.trafficsim.engine.simulation;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;

public interface SimulationService {

	void execute(String outcomeName, Network network, OdMatrix odMatrix);

	void execute(String outcomeName, Network network, OdMatrix odMatrix,
			SimulationSettings settings);
}
