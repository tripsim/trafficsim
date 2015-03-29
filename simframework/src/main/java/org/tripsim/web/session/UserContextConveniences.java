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
package org.tripsim.web.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.simulation.SimulationProjectBuilder;
import org.tripsim.engine.simulation.SimulationSettings;
import org.tripsim.web.Sequence;
import org.tripsim.web.service.ProjectService;
import org.tripsim.web.service.entity.NetworkService;
import org.tripsim.web.service.entity.OdService;

@Service
public class UserContextConveniences {

	@Autowired
	NetworkService networkService;
	@Autowired
	OdService odService;
	@Autowired
	ProjectService projectService;

	@Autowired
	UserContext userContext;

	public void clear() {
		userContext.sequence = null;
		userContext.network = null;
		userContext.odMatrix = null;
		userContext.settings = null;
	}

	public Sequence getSequence() {
		if (userContext.sequence == null) {
			userContext.sequence = projectService.newSequence();
		}
		return userContext.sequence;
	}

	public void setSequence(Sequence sequence) {
		userContext.sequence = sequence;
	}

	public void setSequence(long init) {
		userContext.sequence = projectService.newSequence(init);
	}

	public Network getNetwork() {
		if (userContext.network == null) {
			userContext.network = networkService.createNetwork();
		}
		return userContext.network;
	}

	public void setNetwork(Network network) {
		userContext.network = network;
		userContext.odMatrix = null;
	}

	public OdMatrix getOdMatrix() {
		if (userContext.odMatrix == null) {
			userContext.odMatrix = odService.createOdMatrix(getNetwork()
					.getName());
		}
		return userContext.odMatrix;
	}

	public void setOdMatrix(OdMatrix odMatrix) {
		userContext.odMatrix = odMatrix;
	}

	public SimulationSettings getSettings() {
		if (userContext.settings == null) {
			userContext.settings = projectService.newSettings();
		}
		return userContext.settings;
	}

	public void setSettings(SimulationSettings settings) {
		userContext.settings = settings;
	}

	public SimulationProject asProject() {
		return new SimulationProjectBuilder().withNetwork(getNetwork())
				.withOdMatrix(getOdMatrix()).withSettings(getSettings())
				.build();
	}

	public void importProject(SimulationProject project) {
		userContext.network = project.getNetwork();
		userContext.odMatrix = project.getOdMatrix();
		userContext.settings = project.getSettings();
		setSequence(userContext.network == null ? null : userContext.network
				.getHighestElementId());
	}
}
