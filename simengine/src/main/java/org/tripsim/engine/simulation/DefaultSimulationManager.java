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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.data.dom.SimulationDo;
import org.tripsim.data.persistence.SimulationDao;

@Service("default-simulation-manager")
class DefaultSimulationManager implements SimulationManager {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSimulationManager.class);

	@Autowired
	SimulationDao simulationDao;

	@Override
	public SimulationSettings getDefaultSimulationSettings() {
		return new SimulationSettingsBuilder().build();
	}

	@Override
	public String insertSimulation(String simulationName, String networkName,
			String odMatrixName, SimulationSettings settings) {
		SimulationDo entity = SimulationSettingsConverter.toSimulationDo(
				simulationName, networkName, odMatrixName, settings);
		return simulationDao.insert(entity);
	}

	@Override
	public List<String> getSimulationNames(String networkName) {
		return simulationDao.getSimulationNames(networkName);
	}

	@Override
	public ExecutedSimulation findSimulation(String name) {
		return SimulationSettingsConverter.toSimulation(simulationDao
				.findByName(name));
	}

	@Override
	public ExecutedSimulation findLatestSimulation(String networkName) {
		return SimulationSettingsConverter.toSimulation(simulationDao
				.findLatest(networkName));
	}
}
