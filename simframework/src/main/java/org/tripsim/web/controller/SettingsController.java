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

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tripsim.engine.simulation.SimulationService;
import org.tripsim.util.TimeUtils;
import org.tripsim.web.service.ProjectService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/settings")
public class SettingsController extends AbstractController {

	@Autowired
	ProjectService settingsService;
	@Autowired
	SimulationService simulationService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String settingsView(Model model) {
		model.addAttribute("simulationName", newSimulationName());
		model.addAttribute("settings", context.getSettings());
		return "components/settings";
	}

	private String newSimulationName() {
		return "Simulation " + TimeUtils.newDate();
	}

	@RequestMapping(value = "/run", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> runSimulation(
			@RequestParam("simulationName") String name,
			@RequestParam("duration") int duration,
			@RequestParam("stepSize") double stepSize,
			@RequestParam("warmup") int warmup, @RequestParam("seed") long seed) {
		settingsService.updateSettings(context.getSettings(), duration,
				stepSize, warmup, seed);
		simulationService.execute(name, context.getNetwork(),
				context.getOdMatrix(), context.getSettings());
		return successResponse("Simulation started.");
	}

}
