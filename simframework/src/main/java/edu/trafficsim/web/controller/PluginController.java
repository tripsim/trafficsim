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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.plugin.PluginManager;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/plugin")
public class PluginController extends AbstractController {

	@Autowired
	PluginManager pluginManager;
	@Autowired
	TypesManager typesManager;

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String managePlugins(Model model) {
		addModelAttribute(model);
		return "components/plugin-manager";
	}

	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public String viewPlugins(Model model) {
		addModelAttribute(model);
		return "components/plugin-types";
	}

	private void addModelAttribute(Model model) {
		model.addAttribute("simulatings", pluginManager.getSimulatings());
		model.addAttribute("routings", pluginManager.getRoutings());
		model.addAttribute("generatings", pluginManager.getVehicleGeneratings());
		model.addAttribute("movings", pluginManager.getMovings());
		model.addAttribute("carfollowings", pluginManager.getCarFollowings());
		model.addAttribute("lanechangings", pluginManager.getLaneChangings());

		model.addAttribute("vehicleTypes", typesManager.getVehicleTypes());
		model.addAttribute("driverTypes", typesManager.getVehicleTypes());
	}
}
