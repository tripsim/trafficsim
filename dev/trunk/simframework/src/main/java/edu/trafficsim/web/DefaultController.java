package edu.trafficsim.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.ActionJsonResponseService;
import edu.trafficsim.web.service.DemoSimulationService;
import edu.trafficsim.web.service.ExtractOsmNetworkService;
import edu.trafficsim.web.service.JsonOutputService;

@Controller
public class DefaultController {

	@Autowired
	SimulationProject project;

	@Autowired
	DemoSimulationService demoSimulationService;
	@Autowired
	ExtractOsmNetworkService extractOsmNetworkService;
	@Autowired
	JsonOutputService jsonOutputService;
	@Autowired
	ActionJsonResponseService actionJsonResponse;

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
	String demoNetwork() {
		String str = "";
		try {
			project.setNetwork(demoSimulationService.getScenario().getNetwork());
			project.setOdMatrix(demoSimulationService.getScenario()
					.getOdMatrix());
			project.setSimulator(demoSimulationService.getScenario()
					.getSimulator());
			str = jsonOutputService.getNetworkJson(project.getNetwork());
		} catch (TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@RequestMapping(value = "/createnetwork", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createNetwork(@RequestParam("bbox") String bbox,
			@RequestParam("highway") String highway) {
		Network network;
		// TODO build feedbacks
		try {
			network = extractOsmNetworkService.createNetwork(bbox, highway,
					project.getNetworkFactory());
			project.setNetwork(network);
			return actionJsonResponse.successResponse("network created",
					"view/network", jsonOutputService.getNetworkJson(network));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModelInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actionJsonResponse.failureResponse("Network generation failed.");
	}
}
