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

import java.io.Serializable;

import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;

public class SimulationProject implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final long DEFAULT_INIT_SEQ = 1000000000;

	String simulationName;
	Network network;
	OdMatrix odMatrix;
	SimulationSettings settings;
	long nextSeq = DEFAULT_INIT_SEQ;

	SimulationProject(String simulationName, Network network, OdMatrix odMatrix) {
		this.simulationName = simulationName;
		this.network = network;
		this.odMatrix = odMatrix;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public Network getNetwork() {
		return network;
	}

	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	public SimulationSettings getSettings() {
		return settings;
	}

	void setSettings(SimulationSettings settings) {
		this.settings = settings;
	}

	public long getNextSeq() {
		return nextSeq;
	}

	void setNextSeq(long nextSeq) {
		this.nextSeq = nextSeq;
	}

	public boolean isReady() {
		return network != null && odMatrix != null && settings != null
				&& odMatrix.getNetworkName() != null
				&& network.getName() != null
				&& odMatrix.getNetworkName().equals(network.getName());
	}

	@Override
	public String toString() {
		return "SimulationProject [simulationName=" + simulationName
				+ ", network=" + network + ", odMatrix=" + odMatrix
				+ ", settings=" + settings + ", nextSeq=" + nextSeq + "]";
	}

}
