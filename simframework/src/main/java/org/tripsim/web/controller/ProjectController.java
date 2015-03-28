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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.engine.io.IOService;
import org.tripsim.engine.network.NetworkManager;
import org.tripsim.engine.od.OdManager;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.web.service.ProjectService;
import org.tripsim.web.service.entity.OdService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectController extends AbstractController {

	@Autowired
	NetworkManager networkManager;
	@Autowired
	OdManager odManager;
	@Autowired
	OdService odService;
	@Autowired
	ProjectService projectService;

	@Autowired
	IOService ioService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newProjectView(Model model) {
		model.addAttribute("network", context.getNetwork());
		model.addAttribute("odMatrix", context.getOdMatrix());
		model.addAttribute("networkNames", networkManager.getNetworkNames());
		model.addAttribute("odMatrixNames",
				odManager.getOdMatrixNames(context.getNetwork().getName()));
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
			context.setNetwork(network);
			return successResponseWithRedirect("network loaded!", "/");
		}
		if ("odMatrix".equals(element)) {
			OdMatrix odMatrix = odManager.loadOdMatrix(name);
			if (odMatrix == null) {
				return failureResponse("network " + name + "doesn't exists!");
			}
			context.setOdMatrix(odMatrix);
			return successResponseWithRedirect("odMatrix loaded!", "/");
		}
		return failureResponse("unknown element to load!");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(
			@RequestParam("name") String name,
			@RequestParam("element") String element, Model model) {
		if ("network".equals(element)) {
			Network network = context.getNetwork();
			network.setName(name);
			networkManager.saveNetwork(network);
			return successResponse("network saved!");
		}
		if ("odMatrix".equals(element)) {
			OdMatrix odMatrix = context.getOdMatrix();
			odMatrix.setName(name);
			odManager.saveOdMatrix(odMatrix);
			return successResponse("odMatrix saved!");
		}
		return failureResponse("unknown element to save!");
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportProject(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"project.json\"");
		try {
			ioService.exportProject(context.asProject(),
					response.getOutputStream());
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
				context.importProject(project);
			} catch (IOException e) {
			}
		}
		return "redirect:/";
	}
}
