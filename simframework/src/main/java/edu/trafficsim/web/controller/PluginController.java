/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.plugin.PluginManager;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/plugin")
@SessionAttributes(value = { "typesLibrary" })
public class PluginController extends AbstractController {

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String managePlugins(
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {

		model.addAttribute("simulatings", PluginManager.getSimulatingKeys());
		model.addAttribute("routings", PluginManager.getRoutingKeys());
		model.addAttribute("generatings",
				PluginManager.getVehicleGeneratingKeys());
		model.addAttribute("movings", PluginManager.getMovingKeys());
		model.addAttribute("carfollowings", PluginManager.getCarFollowingKeys());
		model.addAttribute("lanechangings", PluginManager.getLaneChangingKeys());

		model.addAttribute("vehicleTypes", library.getVehicleTypes());
		return "components/plugin-manager";
	}

	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public String viewPlugins(
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {

		model.addAttribute("simulatings", PluginManager.getSimulatingKeys());
		model.addAttribute("routings", PluginManager.getRoutingKeys());
		model.addAttribute("generatings",
				PluginManager.getVehicleGeneratingKeys());
		model.addAttribute("movings", PluginManager.getMovingKeys());
		model.addAttribute("carfollowings", PluginManager.getCarFollowingKeys());
		model.addAttribute("lanechangings", PluginManager.getLaneChangingKeys());

		return "components/plugin-types";
	}
}
