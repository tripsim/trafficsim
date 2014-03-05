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

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.service.StatisticsService;

@Controller
@RequestMapping(value = "/results")
@SessionAttributes(value = { "networkFactory", "network" })
public class ResultsController extends AbstractController {

	@Autowired
	SimulationProject project;
	@Autowired
	StatisticsService statisticsService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(
			@ModelAttribute("statistics") StatisticsCollector statistics,
			Model model) {
		if (!statistics.isDone())
			return "components/empty";

		model.addAttribute("linkIds", statistics.getLinkIds());
		model.addAttribute("vehicleIds", statistics.getVehicleIds());
		return "components/results";
	}

	@RequestMapping(value = "/frames", method = RequestMethod.GET)
	public @ResponseBody
	String anmation(@ModelAttribute("statistics") StatisticsCollector statistics) {
		return statisticsService.getFrames(statistics);
	}

	@RequestMapping(value = "/trajectory/{vid}", method = RequestMethod.GET)
	public @ResponseBody
	String trajectory(@PathVariable long vid,
			@ModelAttribute("statistics") StatisticsCollector statistics,
			@ModelAttribute("networkFactory") NetworkFactory factory) {
		return statisticsService.getTrajectory(statistics, factory, vid);
	}

	@RequestMapping(value = "/tsd/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String tsd(@PathVariable long id,
			@ModelAttribute("statistics") StatisticsCollector statistics) {
		return statisticsService.getTsdPlotData(statistics, id);
	}

	@ModelAttribute("statistics")
	public StatisticsCollector getStatistics() {
		return project.getStatistics();
	}
}
