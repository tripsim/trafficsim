package edu.trafficsim.engine.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.data.persistence.SimulationDao;
import edu.trafficsim.engine.network.NetworkManager;
import edu.trafficsim.engine.od.OdManager;
import edu.trafficsim.plugin.SimulationJobController;

@Service("default-simulation-service")
public class DefaultSimulationService implements SimulationService {

	@Autowired
	NetworkManager networkManager;
	@Autowired
	OdManager odManager;
	@Autowired
	SimulationManager simulationManager;
	@Autowired
	SimulationDao simulationDao;

	@Autowired
	SimulationJobController controller;

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

		SimulationProject project = new SimulationProjectBuilder(simulationName)
				.withNetwork(network).withOdMatrix(odMatrix)
				.withSettings(settings).build();
		controller.submitJob(project);
	}

	@Override
	public boolean isRunning(String simulationName) {
		return controller.isSimulationRunning(simulationName);
	}

}
