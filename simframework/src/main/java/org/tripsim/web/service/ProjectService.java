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
package org.tripsim.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.engine.simulation.SimulationManager;
import org.tripsim.engine.simulation.SimulationSettings;
import org.tripsim.web.Sequence;

@Service("project-service")
public class ProjectService {

	@Autowired
	SimulationManager simulationManager;

	public void updateSettings(SimulationSettings settings, int duration,
			double stepSize, int warmup, long seed) {
		settings.setDuration(duration);
		settings.setStepSize(stepSize);
		settings.setWarmup(warmup);
		settings.setSeed(seed);
	}

	public SimulationSettings newSettings() {
		return simulationManager.getDefaultSimulationSettings();
	}

	public Sequence newSequence() {
		return new Sequence();
	}

	public Sequence newSequence(long init) {
		return new Sequence(init);
	}
}
