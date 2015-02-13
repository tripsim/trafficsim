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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edu.trafficsim.engine.io.IOService;
import edu.trafficsim.engine.io.SimulationProject;
import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;

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
	IOService ioService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newProjectView() {
		return "components/project";
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
			ioService.exportProject(new SimulationProject(network, odMatrix,
					settings), response.getOutputStream());
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
