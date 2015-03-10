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

import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;

public class SimulationProjectBuilder {

	public static final long DEFAULT_INIT_SEQ = 1000000000;

	String simulationName;
	Network network;
	OdMatrix odMatrix;
	SimulationSettings settings = null;
	long nextSeq = DEFAULT_INIT_SEQ;

	public SimulationProjectBuilder(String simulationName) {
		this.simulationName = simulationName;
	}

	public SimulationProjectBuilder() {
		this("");
	}

	public SimulationProjectBuilder withNetwork(Network network) {
		this.network = network;
		return this;

	}

	public SimulationProjectBuilder withOdMatrix(OdMatrix odMatrix) {
		this.odMatrix = odMatrix;
		return this;
	}

	public SimulationProjectBuilder withSettings(SimulationSettings settings) {
		this.settings = settings;
		return this;
	}

	public SimulationProjectBuilder withSeq(long seq) {
		this.nextSeq = seq;
		return this;
	}

	public SimulationProject build() {
		SimulationProject project = new SimulationProject(simulationName,
				network, odMatrix);
		project.setSettings(settings);
		project.setNextSeq(nextSeq);
		return project;
	}
}
