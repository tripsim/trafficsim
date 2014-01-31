package edu.trafficsim.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.Network;
import edu.trafficsim.web.service.JsonOutputService;

@Controller
@RequestMapping(value = "/json")
public class JsonController {
	@Autowired
	SimulationProject project;

	@Autowired
	JsonOutputService jsonOutputService;

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public @ResponseBody
	String getNetwork() {
		Network network = project.getNetwork();
		String str = jsonOutputService.getNetworkJson(network);
		return str;
	}

	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public @ResponseBody
	String getCenter() {
		Network network = project.getNetwork();
		String str = jsonOutputService.getCenterJson(network);
		return str;
	}

	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getLink(@PathVariable long id) {
		Network network = project.getNetwork();
		String str = jsonOutputService.getLinkJson(network, id);
		return str;
	}

	@RequestMapping(value = "/lanes/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getLanes(@PathVariable long id) {
		Network network = project.getNetwork();
		String str = jsonOutputService.getLanesJson(network, id);
		return str;
	}

}
