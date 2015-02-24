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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.simulation.ExecutedSimulation;
import edu.trafficsim.engine.simulation.SimulationManager;
import edu.trafficsim.engine.simulation.SimulationService;
import edu.trafficsim.model.Network;
import edu.trafficsim.web.service.FdDto;
import edu.trafficsim.web.service.FramesDto;
import edu.trafficsim.web.service.StatisticsService;
import edu.trafficsim.web.service.TrajectoriesDto;
import edu.trafficsim.web.service.TsdDto;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/results")
@SessionAttributes(value = { "network" })
public class ResultsController extends AbstractController {

	@Autowired
	SimulationService simulationService;
	@Autowired
	StatisticsService statisticsService;

	@Autowired
	SimulationManager simulationManager;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam("simulationName") String simulationName,
			@ModelAttribute("network") Network network, Model model) {
		List<String> simulationNames = simulationManager
				.getSimulationNames(network.getName());

		ExecutedSimulation simulation = simulationName == null ? simulationManager
				.findLatestSimulation(network.getName()) : simulationManager
				.findSimulation(simulationName);
		if (simulationNames.isEmpty() || simulation == null) {
			return "components/empty";
		}

		model.addAttribute("latestSimulation", simulation);
		model.addAttribute("simulationNames", simulationNames);
		return "components/results";
	}

	@RequestMapping(value = "/frames/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody FramesDto getFrames(
			@PathVariable("simulationName") String simulationName,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "1000") long limit) {
		return statisticsService.getFrames(simulationName, offset, limit);
	}

	@RequestMapping(value = "/trajectories/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody TrajectoriesDto trajectory(
			@PathVariable("simulationName") String simulationName,
			@RequestParam("nodeId") long nodeId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "1000") long limit) {
		return statisticsService.getTrajectories(simulationName, nodeId,
				offset, limit);
	}

	@RequestMapping(value = "/tsd/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody TsdDto getTsd(
			@PathVariable("simulationName") String simulationName,
			@RequestParam("linkId") long linkId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "1000") long limit) {
		return statisticsService.getTsd(simulationName, linkId, offset, limit);
	}

	@RequestMapping(value = "/fd/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody FdDto getFd(
			@PathVariable("simulationName") String simulationName,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "1000") long limit) {
		return statisticsService.getFd(simulationName, offset, limit);
	}
}
