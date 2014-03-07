package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.plugin.PluginManager;

@Controller
@RequestMapping(value = "/plugin")
@SessionAttributes(value = { "typesLibrary" })
public class PluginController extends AbstractController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewPlugins(
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {

		model.addAttribute("simulatings", PluginManager.getSimulatingKeys());
		model.addAttribute("routings", PluginManager.getRoutingKeys());
		model.addAttribute("generatings",
				PluginManager.getVehicleGeneratingKeys());
		model.addAttribute("movings", PluginManager.getMovingKeys());
		model.addAttribute("carfollowings", PluginManager.getCarFollowingKeys());
		model.addAttribute("lanechangings", PluginManager.getLaneChangingKeys());

		model.addAttribute("vehicleTypes", library.getVehicleTypes());
		return "components/plugin";
	}
}
