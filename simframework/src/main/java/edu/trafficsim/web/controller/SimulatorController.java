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

import java.util.Map;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.utility.Timer;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.service.SimulationService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/simulator")
@SessionAttributes(value = { "sequence", "network", "odMatrix" })
public class SimulatorController extends AbstractController {

	@Autowired
	SimulationProject project;
	@Autowired
	SimulationService simulationService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String simulatorView(Model model) {
		Timer timer = project.getTimer();
		model.addAttribute("timer", timer);
		return "components/simulator";
	}

	@RequestMapping(value = "/run", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> runSimulation(@RequestParam("duration") int duration,
			@RequestParam("warmup") int warmup,
			@RequestParam("stepSize") double stepSize,
			@RequestParam("seed") long seed,
			@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		simulationService.setTimer(project, stepSize, duration, warmup, seed);

		try {
			simulationService.runSimulation(project, seq, network, odMatrix);
		} catch (TransformException e) {
			return failureResponse("Transform issues!");
		}
		return messageOnlySuccessResponse("Simulation finished.");
	}

}
