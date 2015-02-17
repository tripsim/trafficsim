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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.statistics.StatisticsCollector;
import edu.trafficsim.web.service.StatisticsService;

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
	StatisticsService statisticsService;

//	@RequestMapping(value = "/view", method = RequestMethod.GET)
//	public String view(
//			@ModelAttribute("statistics") StatisticsCollector statistics,
//			Model model) {
//		if (!statistics.isDone())
//			return "components/empty";
//
//		model.addAttribute("linkIds", statistics.getLinkIds());
//		model.addAttribute("vehicleIds", statistics.getVehicleIds());
//		return "components/results";
//	}
//
//	@RequestMapping(value = "/frames", method = RequestMethod.GET)
//	public @ResponseBody
//	String anmation(@ModelAttribute("statistics") StatisticsCollector statistics) {
//		return statisticsService.getFrames(statistics);
//	}
//
//	@RequestMapping(value = "/trajectory/{vid}", method = RequestMethod.GET)
//	public @ResponseBody
//	String trajectory(@PathVariable long vid,
//			@ModelAttribute("statistics") StatisticsCollector statistics,
//			@ModelAttribute("networkFactory") NetworkFactory factory) {
//		return statisticsService.getTrajectory(statistics, factory, vid);
//	}
//
//	@RequestMapping(value = "/tsd/{id}", method = RequestMethod.GET)
//	public @ResponseBody
//	String tsd(@PathVariable long id,
//			@ModelAttribute("statistics") StatisticsCollector statistics) {
//		return statisticsService.getTsdPlotData(statistics, id);
//	}
//
//	@ModelAttribute("statistics")
//	public StatisticsCollector getStatistics() {
//		return project.getStatistics();
//	}
}
