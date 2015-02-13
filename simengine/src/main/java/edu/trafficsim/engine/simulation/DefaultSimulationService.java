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

	@Override
	public void execute(Network network, OdMatrix odMatrix,
			SimulationSettings settings) {
		ISimulating impl = pluginManager.getSimulatingImpl(settings
				.getSimulatingType());
		impl.simulate(network, odMatrix, settings);
	}

}
