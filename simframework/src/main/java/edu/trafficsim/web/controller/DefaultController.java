/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.web.controller;

import javax.servlet.http.HttpSession;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.simulation.SimulationManager;
import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.Sequence;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.NetworkService;
import edu.trafficsim.web.service.entity.OdService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes(value = { "seq", "network", "odMatrix", "settings" })
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
	NetworkFactory networkFactory;
	@Autowired
	OdFactory odFactory;

	@RequestMapping(value = "/")
	public String home(Model model, HttpSession session)
			throws ModelInputException {
		if (session.isNew()) {
			Sequence sequence = new Sequence();
			Network network = networkService.createNetwork(sequence);
			OdMatrix odMatrix = odService.createOdMatrix(sequence);
			SimulationSettings settings = simulationManager
					.getDefaultSimulationSettings();

			model.addAttribute("seq", sequence);
			model.addAttribute("network", network);
			model.addAttribute("odMatrix", odMatrix);
			model.addAttribute("settings", settings);
		}
		return "index";
	}

	@RequestMapping(value = "getdemonetwork", method = RequestMethod.GET)
	public @ResponseBody String demoNetwork(Model model)
			throws ModelInputException, FactoryException, TransformException {
		DemoBuilder demo = new DemoBuilder();
		model.addAttribute("sequence", new Sequence(demo.getNextId()));
		model.addAttribute("network", demo.getNetwork());
		model.addAttribute("odMatrix", demo.getOdMatrix());
		return mapJsonService.getNetworkJson(demo.getNetwork());
	}
}
