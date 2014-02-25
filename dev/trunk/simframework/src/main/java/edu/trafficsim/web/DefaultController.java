package edu.trafficsim.web;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.DemoSimulationService;
import edu.trafficsim.web.service.ImportScenarioService;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.OsmImportService;

@Controller
public class DefaultController {

	@Autowired
	SimulationProject project;

	@Autowired
	DemoSimulationService demoSimulationService;
	@Autowired
	ImportScenarioService importScenarioService;
	@Autowired
	OsmImportService extractOsmNetworkService;
	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/")
	public String home() {
		System.out.println("HomeController: Passing through...");
		return "index";
	}

	// TODO remove it
	@RequestMapping(value = "/loaddemo", method = RequestMethod.GET)
	public @ResponseBody
	String demoSimulation() {
		String str = "";
		try {
			str = demoSimulationService.runSimulation();
		} catch (TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	// TODO remove it
	@RequestMapping(value = "/getdemonetwork", method = RequestMethod.GET)
	public @ResponseBody
	String demoNetwork() throws ModelInputException, UserInterfaceException {
		String str = "";
		try {
			importScenarioService.updateProject(demoSimulationService
					.getScenario());
			str = mapJsonService.getNetworkJson(project.getNetwork());
		} catch (TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

}
