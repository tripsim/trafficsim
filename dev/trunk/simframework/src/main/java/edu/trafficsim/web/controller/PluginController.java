package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.plugin.PluginManager;

@Controller
@RequestMapping(value = "/plugin")
public class PluginController extends AbstractController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewPlugin(Model model) {

		model.addAttribute("simulatings", PluginManager.getSimulatingKeys());
		model.addAttribute("routings", PluginManager.getRoutingKeys());
		model.addAttribute("generatings",
				PluginManager.getVehicleGeneratingKeys());
		return "components/plugin";
	}

}
