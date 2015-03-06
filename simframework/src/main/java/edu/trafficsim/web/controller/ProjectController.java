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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.engine.io.IOService;
import edu.trafficsim.engine.network.NetworkManager;
import edu.trafficsim.engine.od.OdManager;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.simulation.SimulationProjectBuilder;
import edu.trafficsim.engine.simulation.SimulationSettings;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/project")
@SessionAttributes(value = { "network", "odMatrix", "settings" })
public class ProjectController extends AbstractController {

	@Autowired
	NetworkManager networkManager;
	@Autowired
	OdManager odManager;

	@Autowired
	IOService ioService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newProjectView(@ModelAttribute("network") Network network,
			Model model) {
		model.addAttribute("networkNames", networkManager.getNetworkNames());
		model.addAttribute("odMatrixNames",
				odManager.getOdMatrixNames(network.getName()));
		return "components/project";
	}

	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> load(
			@RequestParam("name") String name,
			@RequestParam("element") String element, Model model) {
		if ("network".equals(element)) {
			Network network = networkManager.loadNetwork(name);
			if (network == null) {
				return failureResponse("network " + name + "doesn't exists!");
			}
			model.addAttribute("network", network);
			return successResponseWithRedirect("network loaded!", "/");
		}
		if ("odMatrix".equals(element)) {
			OdMatrix odMatrix = odManager.loadOdMatrix(name);
			if (odMatrix == null) {
				return failureResponse("network " + name + "doesn't exists!");
			}
			model.addAttribute("odMatrix", odMatrix);
			return successResponseWithRedirect("odMatrix loaded!", "/");
		}
		return failureResponse("unknown element to load!");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(
			@RequestParam("name") String name,
			@RequestParam("element") String element,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix, Model model) {
		if ("network".equals(element)) {
			network.setName(name);
			networkManager.saveNetwork(network);
			return successResponse("network saved!");
		}
		if ("odMatrix".equals(element)) {
			odMatrix.setName(name);
			odManager.saveOdMatrix(odMatrix);
			return successResponse("odMatrix saved!");
		}
		return failureResponse("unknown element to save!");
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportProject(@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix,
			@ModelAttribute("settings") SimulationSettings settings,
			HttpServletResponse response) {
		response.setContentType("application/json");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"project.json\"");
		try {
			ioService.exportProject(
					new SimulationProjectBuilder().withNetwork(network)
							.withOdMatrix(odMatrix).withSettings(settings)
							.build(), response.getOutputStream());
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String importProject(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		if (!multipartFile.isEmpty()) {
			try (InputStream in = multipartFile.getInputStream()) {
				SimulationProject project = ioService.importProject(in);
				model.addAttribute("network", project.getNetwork());
				model.addAttribute("odMatrix", project.getOdMatrix());
				model.addAttribute("settings", project.getSettings());
			} catch (IOException e) {
			}
		}
		return "redirect:/";
	}
}
