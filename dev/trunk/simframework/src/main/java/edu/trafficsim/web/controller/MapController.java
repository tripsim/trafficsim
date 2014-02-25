package edu.trafficsim.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.Network;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.service.MapJsonService;

@Controller
@RequestMapping(value = "/map")
public class MapController {
	@Autowired
	SimulationProject project;

	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public @ResponseBody
	String getNetwork() {
		Network network = project.getNetwork();
		String str = mapJsonService.getNetworkJson(network);
		return str;
	}

	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public @ResponseBody
	String getCenter() {
		Network network = project.getNetwork();
		String str = mapJsonService.getCenterJson(network);
		return str;
	}

	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getLink(@PathVariable long id) {
		Network network = project.getNetwork();
		String str = mapJsonService.getLinkJson(network, id);
		return str;
	}

	@RequestMapping(value = "/lanes/{linkId}", method = RequestMethod.GET)
	public @ResponseBody
	String getLanes(@PathVariable long linkId) {
		Network network = project.getNetwork();
		String str = mapJsonService.getLanesJson(network, linkId);
		return str;
	}

}
