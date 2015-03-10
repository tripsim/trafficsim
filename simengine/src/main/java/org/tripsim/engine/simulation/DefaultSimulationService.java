package org.tripsim.engine.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.data.persistence.SimulationDao;
import org.tripsim.engine.network.NetworkManager;
import org.tripsim.engine.od.OdManager;
import org.tripsim.plugin.SimulationJobController;

@Service("default-simulation-service")
public class DefaultSimulationService implements SimulationService {

	private final static Logger logger = LoggerFactory
			.getLogger(DefaultSimulationService.class);

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
		logger.info("Start executing Simulation {}", simulationName);
		if (network.isModified()) {
			logger.info("Saving network '{}'", network.getName());
			networkManager.saveNetwork(network);
			network.setModified(false);
		}
		if (odMatrix.isModified()) {
			logger.info("Saving odMatrix '{}'", odMatrix.getName());
			odManager.saveOdMatrix(odMatrix);
			odMatrix.setModified(false);
		}

		simulationName = simulationManager.insertSimulation(simulationName,
				network.getName(), odMatrix.getName(), settings);
		logger.info("Inserted simulation as {}.", simulationName);
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
