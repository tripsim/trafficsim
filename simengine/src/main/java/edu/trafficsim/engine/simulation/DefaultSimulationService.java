package edu.trafficsim.engine.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.PluginManager;

@Service("default-simulation-service")
public class DefaultSimulationService implements SimulationService {

	@Autowired
	PluginManager pluginManager;
	@Autowired
	SimulationManager simulationManager;

	@Override
	public void execute(Network network, OdMatrix odMatrix) {
		execute(network, odMatrix,
				simulationManager.getDefaultSimulationSettings());
	}

	@Override
	public void execute(Network network, OdMatrix odMatrix,
			SimulationSettings settings) {
		ISimulating impl = pluginManager.getSimulatingImpl(settings
				.getSimulatingType());

		long simulationId = System.currentTimeMillis();
		// TODO save simulation settings
		impl.simulate(simulationId, network, odMatrix, settings);
	}

}
