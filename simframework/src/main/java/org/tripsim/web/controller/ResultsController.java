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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tripsim.api.model.Network;
import org.tripsim.engine.simulation.ExecutedSimulation;
import org.tripsim.engine.simulation.SimulationManager;
import org.tripsim.engine.simulation.SimulationService;
import org.tripsim.web.service.statistics.FdDto;
import org.tripsim.web.service.statistics.FramesDto;
import org.tripsim.web.service.statistics.MeasureDto;
import org.tripsim.web.service.statistics.StatisticsService;
import org.tripsim.web.service.statistics.TrajectoriesDto;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/results")
public class ResultsController extends AbstractController {

	@Autowired
	SimulationService simulationService;
	@Autowired
	StatisticsService statisticsService;

	@Autowired
	SimulationManager simulationManager;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(
			@RequestParam(value = "simulationName", required = false) String simulationName,
			Model model) {
		Network network = context.getNetwork();
		List<String> simulationNames = simulationManager
				.getSimulationNames(network.getName());
		if (simulationNames.isEmpty()) {
			return "components/empty-insert";
		}
		ExecutedSimulation simulation = simulationName == null ? simulationManager
				.findLatestSimulation(network.getName()) : simulationManager
				.findSimulation(simulationName);

		model.addAttribute("latestSimulation", simulation);
		model.addAttribute("simulationNames", simulationNames);
		return "components/results";
	}

	@RequestMapping(value = "/simulation/{simulationName}", method = RequestMethod.GET)
	public String viewSimulation(
			@PathVariable("simulationName") String simulationName, Model model) {
		ExecutedSimulation simulation = simulationManager
				.findSimulation(simulationName);
		if (simulation == null) {
			return "components/empty-insert";
		}
		model.addAttribute("latestSimulation", simulation);
		return "components/results :: simulation";
	}

	@RequestMapping(value = "/frames/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody FramesDto getFrames(
			@PathVariable("simulationName") String simulationName,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "200") long limit) {
		return statisticsService.getFrames(simulationName, offset, limit);
	}

	@RequestMapping(value = "/trajectories/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody TrajectoriesDto trajectory(
			@PathVariable("simulationName") String simulationName,
			@RequestParam("nodeId") long nodeId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "200") long limit) {
		return statisticsService.getTrajectories(simulationName, nodeId,
				offset, limit);
	}

	@RequestMapping(value = "/tsd/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody MeasureDto getTsd(
			@PathVariable("simulationName") String simulationName,
			@RequestParam("linkId") long linkId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "200") long limit) {
		return statisticsService.getTsd(simulationName, linkId, offset, limit);
	}

	@RequestMapping(value = "/linkVolumes/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody MeasureDto getLinkVolumes(
			@PathVariable("simulationName") String simulationName,
			@RequestParam("linkId") long linkId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "200") long limit) {
		return statisticsService.getVolumes(simulationName, linkId, offset,
				limit);
	}

	@RequestMapping(value = "/linkSpeeds/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody MeasureDto getLinkSpeeds(
			@PathVariable("simulationName") String simulationName,
			@RequestParam("linkId") long linkId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "200") long limit) {
		return statisticsService.getAvgSpeeds(simulationName, linkId, offset,
				limit);
	}

	@RequestMapping(value = "/fd/{simulationName}", method = RequestMethod.GET)
	public @ResponseBody FdDto getFd(
			@PathVariable("simulationName") String simulationName,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "200") long limit) {
		return statisticsService.getFd(simulationName, offset, limit);
	}
}
