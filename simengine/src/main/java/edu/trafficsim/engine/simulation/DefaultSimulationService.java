package edu.trafficsim.engine.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.network.NetworkManager;
import edu.trafficsim.engine.od.OdManager;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.PluginManager;

@Service("default-simulation-service")
public class DefaultSimulationService implements SimulationService {

	@Autowired
	PluginManager pluginManager;
	@Autowired
	NetworkManager networkManager;
	@Autowired
	OdManager odManager;
	@Autowired
	SimulationManager simulationManager;

	@Override
	public void execute(String outcomeName, Network network, OdMatrix odMatrix) {
		execute(outcomeName, network, odMatrix,
				simulationManager.getDefaultSimulationSettings());
	}

	@Override
	public void execute(String outcomeName, Network network, OdMatrix odMatrix,
			SimulationSettings settings) {
		if (network.isModified()) {
			networkManager.insertNetwork(network);
			network.setModified(false);
		}
		if (odMatrix.isModified()) {
			odManager.insertOdMatrix(odMatrix);
			network.setModified(false);
		}

		simulationManager.insertSimulation(outcomeName, network.getName(),
				odMatrix.getName(), settings);

		ISimulating impl = pluginManager.getSimulatingImpl(settings
				.getSimulatingType());
		impl.simulate(outcomeName, network, odMatrix, settings);
	}

}
