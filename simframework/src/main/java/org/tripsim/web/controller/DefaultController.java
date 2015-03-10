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
package org.tripsim.web.controller;

import javax.servlet.http.HttpSession;

import org.opengis.referencing.FactoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.engine.demo.DemoBuilder;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.engine.od.OdFactory;
import org.tripsim.engine.simulation.SimulationManager;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.simulation.SimulationSettings;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.web.Sequence;
import org.tripsim.web.service.MapJsonService;
import org.tripsim.web.service.entity.NetworkService;
import org.tripsim.web.service.entity.OdService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes(value = { "sequence", "network", "odMatrix", "settings" })
public class DefaultController extends AbstractController {

	@Autowired
	MapJsonService mapJsonService;
	@Autowired
	NetworkService networkService;
	@Autowired
	OdService odService;

	@Autowired
	SimulationManager simulationManager;
	@Autowired
	TypesManager typesManager;
	@Autowired
	NetworkFactory networkFactory;
	@Autowired
	OdFactory odFactory;

	@Autowired
	DemoBuilder demoBuilder;

	@RequestMapping(value = "/")
	public String home(Model model, HttpSession session) {
		if (session.isNew()) {
			return "redirect:/new";
		}
		return "index";
	}

	@RequestMapping(value = "/new")
	public String newProject(Model model, HttpSession session) {
		Sequence sequence = new Sequence();
		Network network = networkService.createNetwork();
		OdMatrix odMatrix = odService.createOdMatrix(sequence,
				network.getName());
		SimulationSettings settings = simulationManager
				.getDefaultSimulationSettings();

		model.addAttribute("sequence", sequence);
		model.addAttribute("network", network);
		model.addAttribute("odMatrix", odMatrix);
		model.addAttribute("settings", settings);
		return "redirect:/";
	}

	@RequestMapping(value = "getdemonetwork", method = RequestMethod.GET)
	public @ResponseBody String demoNetwork(Model model)
			throws FactoryException {
		SimulationProject demo = demoBuilder.getDemo();
		model.addAttribute("sequence", new Sequence(demo.getNextSeq()));
		model.addAttribute("network", demo.getNetwork());
		model.addAttribute("odMatrix", demo.getOdMatrix());
		model.addAttribute("settings",
				simulationManager.getDefaultSimulationSettings());
		return mapJsonService.getNetworkJson(demo.getNetwork());
	}
}
