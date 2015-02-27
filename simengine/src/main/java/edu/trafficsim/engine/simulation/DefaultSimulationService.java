package edu.trafficsim.engine.simulation;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.persistence.SimulationDao;
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
	@Autowired
	SimulationDao simulationDao;

	private final Set<String> runningSimulations = new CopyOnWriteArraySet<String>();

	@Override
	public void execute(String simulationName, Network network,
			OdMatrix odMatrix) {
		execute(simulationName, network, odMatrix,
				simulationManager.getDefaultSimulationSettings());
	}

	@Override
	public void execute(String simulationName, Network network,
			OdMatrix odMatrix, SimulationSettings settings) {
		if (network.isModified()) {
			networkManager.saveNetwork(network);
			network.setModified(false);
		}
		if (odMatrix.isModified()) {
			odManager.saveOdMatrix(odMatrix);
			odMatrix.setModified(false);
		}

		simulationName = simulationManager.insertSimulation(simulationName,
				network.getName(), odMatrix.getName(), settings);
		if (simulationName == null) {
			throw new IllegalStateException(
					"simulation cannot start, no name is given!");
		}
		if (runningSimulations.contains(simulationName)) {
			throw new IllegalStateException("simulation " + simulationName
					+ " is already running!");
		}

		ISimulating impl = pluginManager.getSimulatingImpl(settings
				.getSimulatingType());
		impl.simulate(simulationName, network, odMatrix, settings);
		runningSimulations.remove(simulationName);
	}

	@Override
	public boolean isRunning(String simulationName) {
		return runningSimulations.contains(simulationName);
	}

}
