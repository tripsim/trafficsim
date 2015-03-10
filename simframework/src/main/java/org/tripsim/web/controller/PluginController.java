/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.tripsim.engine.simulation.SimulationSettings;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.plugin.api.PluginManager;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/plugin")
@SessionAttributes(value = { "settings" })
public class PluginController extends AbstractController {

	@Autowired
	PluginManager pluginManager;
	@Autowired
	TypesManager typesManager;

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String managePlugins(
			@ModelAttribute("settings") SimulationSettings settings, Model model) {
		addModelAttribute(model);
		model.addAttribute("settings", settings);
		return "components/plugin-manager";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updatePluginMapping(
			@RequestParam("pluginType") String pluginType,
			@RequestParam("pluginName") String pluginName,
			@RequestParam("type") String type,
			@ModelAttribute("settings") SimulationSettings settings) {
		String message;
		if ("Simulating".equals(pluginType)) {
			settings.setSimulatingType(pluginName);
			message = pluginType + "->" + pluginName;
		} else if ("Generating".equals(pluginType)) {
			settings.setVehicleGeneratingType(pluginName);
			message = pluginType + "->" + pluginName;
		} else if ("Moving".equals(pluginType)) {
			settings.setMovingType(type, pluginName);
			message = pluginType + "->" + type + "->" + pluginName;
		} else if ("Routing".equals(pluginType)) {
			settings.setRoutingType(type, pluginName);
			message = pluginType + "->" + type + "->" + pluginName;
		} else if ("CarFollowing".equals(pluginType)) {
			settings.setCarFollowingType(type, pluginName);
			message = pluginType + "->" + type + "->" + pluginName;
		} else if ("LaneChanging".equals(pluginType)) {
			settings.setLaneChangingType(type, pluginName);
			message = pluginType + "->" + type + "->" + pluginName;
		} else {
			return failureResponse("Unknown plugin type " + pluginType);
		}
		return successResponse("Plugin mapping updated: " + message);
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
