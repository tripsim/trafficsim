package edu.trafficsim.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.model.Network;
import edu.trafficsim.web.service.MapJsonService;

@Controller
@RequestMapping(value = "/map")
@SessionAttributes(value = { "network" })
public class MapController extends AbstractController {

	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public @ResponseBody
	String getNetwork(@ModelAttribute("network") Network network) {
		String str = mapJsonService.getNetworkJson(network);
		return str;
	}

	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public @ResponseBody
	String getCenter(@ModelAttribute("network") Network network) {
		String str = mapJsonService.getCenterJson(network);
		return str;
	}

	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getLink(@PathVariable long id,
			@ModelAttribute("network") Network network) {
		String str = mapJsonService.getLinkJson(network, id);
		return str;
	}

	@RequestMapping(value = "/lanes/{linkId}", method = RequestMethod.GET)
	public @ResponseBody
	String getLanes(@PathVariable long linkId,
			@ModelAttribute("network") Network network) {
		String str = mapJsonService.getLanesJson(network, linkId);
		return str;
	}

}
