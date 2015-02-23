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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.simulation.SimulationService;
import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.web.service.SettingsService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/settings")
@SessionAttributes(value = { "sequence", "network", "odMatrix", "settings" })
public class SettingsController extends AbstractController {

	@Autowired
	SettingsService settingsService;
	@Autowired
	SimulationService simulationService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String settingsView(
			@ModelAttribute("settings") SimulationSettings settings, Model model) {
		model.addAttribute("settings", settings);
		return "components/settings";
	}

	@RequestMapping(value = "/run", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> runSimulation(
			@RequestParam("name") String name,
			@RequestParam("duration") int duration,
			@RequestParam("stepSize") double stepSize,
			@RequestParam("warmup") int warmup,
			@RequestParam("seed") long seed,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix,
			@ModelAttribute("settings") SimulationSettings settings) {
		settingsService.updateSettings(settings, duration, stepSize, warmup,
				seed);
		simulationService.execute(name, network, odMatrix, settings);
		return successResponse("Simulation started.");
	}

}
