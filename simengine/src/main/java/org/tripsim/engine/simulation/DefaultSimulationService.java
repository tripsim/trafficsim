/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
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
class DefaultSimulationService implements SimulationService {

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
