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

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.UserInterfaceException;
import edu.trafficsim.web.service.DemoService;
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
@SessionAttributes(value = { "sequence", "typesFactory", "typesLibrary",
		"networkFactory", "odFactory", "network", "odMatrix" })
public class DefaultController extends AbstractController {

	@Autowired
	SimulationProject project;

	@Autowired
	DemoService demoService;
	@Autowired
	MapJsonService mapJsonService;

	@Autowired
	NetworkService networkService;
	@Autowired
	OdService odService;

	@RequestMapping(value = "/")
	public String home(Model model, HttpSession session)
			throws ModelInputException {
		if (session.isNew()) {
			Sequence seq = Sequence.create();
			NetworkFactory networkFactory = project.getNetworkFactory();
			OdFactory odFactory = project.getOdFactory();
			TypesFactory typesFactory = project.getTypesFactory();
			TypesLibrary typesLibrary = TypesLibrary.defaultLibrary();
			Network network = networkService.createNetwork(networkFactory, seq);
			OdMatrix odMatrix = odService.createOdMatrix(odFactory, seq);

			model.addAttribute("sequence", seq);
			model.addAttribute("typesLibrary", typesLibrary);
			model.addAttribute("networkFactory", networkFactory);
			model.addAttribute("typesFactory", typesFactory);
			model.addAttribute("odFactory", odFactory);
			model.addAttribute("network", network);
			model.addAttribute("odMatrix", odMatrix);
		}
		;
		return "index";
	}

	// TODO make it general import
	@RequestMapping(value = "getdemonetwork", method = RequestMethod.GET)
	public @ResponseBody
	String demoNetwork(@ModelAttribute("typesLibrary") TypesLibrary library,
			Model model) throws ModelInputException, UserInterfaceException {
		String str = "";
		try {
			SimulationScenario scenario = demoService.getScenario();
			model.addAttribute("sequence", scenario.getSequence());
			model.addAttribute("network", scenario.getNetwork());
			model.addAttribute("odMatrix", scenario.getOdMatrix());

			model.addAttribute("typesLibrary", demoService.getTypesLibrary());

			str = mapJsonService.getNetworkJson(scenario.getNetwork());
		} catch (TransformException e) {
			e.printStackTrace();
		}
		return str;
	}
}
