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

import org.tripsim.data.dom.SimulationDo;

final class SimulationSettingsConverter {

	static SimulationDo toSimulationDo(String simulationName, String networkName,
			String odMatrixName, SimulationSettings settings) {
		SimulationDo result = new SimulationDo();
		result.setName(simulationName);
		result.setNetworkName(networkName);
		result.setOdMatrixName(odMatrixName);
		result.setDuration(settings.getDuration());
		result.setStepSize(settings.getStepSize());
		result.setWarmup(settings.getWarmup());
		result.setSeed(settings.getSeed());
		result.setSd(settings.getSd());
		return result;
	}

	static ExecutedSimulation toSimulation(SimulationDo entity) {
		ExecutedSimulation result = new ExecutedSimulation();
		result.setTimestamp(entity.getTimestamp());
		result.setName(entity.getName());
		result.setNetworkName(entity.getNetworkName());
		result.setOdMatrixName(entity.getOdMatrixName());
		SimulationSettings settings = new SimulationSettingsBuilder(
				entity.getDuration(), entity.getStepSize())
				.withWarmup(entity.getWarmup()).withSeed(entity.getSeed())
				.withSd(entity.getSd()).build();
		result.setSettings(settings);
		return result;
	}
}
