package edu.trafficsim.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.web.service.DemoSimulationService;

@Controller
public class DefaultController {

	@Autowired
	DemoSimulationService demoSimulationService;

	@RequestMapping(value = "/")
	public String home() {
		System.out.println("HomeController: Passing through...");
		return "index";
	}

	@RequestMapping(value = "/loaddemo", method = RequestMethod.GET)
	public @ResponseBody
	String demoSimulation() {
		String str = demoSimulationService.runSimulation();
		return str;
	}
	
	@RequestMapping(value = "/loadnetwork", method = RequestMethod.GET)
	public @ResponseBody
	String demoNetwork() {
		String str = demoSimulationService.getNetwork();
		return str;
	}
}
