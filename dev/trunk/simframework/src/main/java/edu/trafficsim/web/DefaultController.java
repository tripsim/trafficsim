package edu.trafficsim.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.demo.SimulationTest;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.DemoSimulationService;
import edu.trafficsim.web.service.ExtractOsmNetworkService;

@Controller
public class DefaultController {

	@Autowired
	DemoSimulationService demoSimulationService;
	@Autowired
	ExtractOsmNetworkService extractOsmNetworkService;

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
		Network network = SimulationTest.getInstance().getNetwork();
		String str = extractOsmNetworkService.getNetwork(network);
		return str;
	}

	@RequestMapping(value = "/createnetwork", method = RequestMethod.GET)
	public @ResponseBody
	String createNetwork() {
		Network network;
		try {
			network = extractOsmNetworkService.createNetwork("");
			String str = extractOsmNetworkService.getNetwork(network);
			return str;
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
		}
		return "";
	}
}
