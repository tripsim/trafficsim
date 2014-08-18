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

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.web.service.entity.NetworkService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/node")
@SessionAttributes(value = { "network", "odMatrix" })
public class NodeController extends AbstractController {

	@Autowired
	NetworkService networkService;

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String nodeView(@PathVariable long id,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix, Model model) {
		if (network == null)
			return "components/empty";
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		if (network.isDirty())
			network.discover();
		model.addAttribute("network", network);
		model.addAttribute("ods", odMatrix.getOdsFromNode(node));
		model.addAttribute("node", node);
		return "components/node";
	}

	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String linkInfo(@PathVariable long id,
			@ModelAttribute("network") Network network, Model model) {
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		model.addAttribute("node", node);
		return "components/node :: info";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String linkEdit(@PathVariable long id,
			@ModelAttribute("network") Network network, Model model) {
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		model.addAttribute("node", node);
		return "components/node-fragments :: form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveNodek(@RequestParam("id") long id,
			@RequestParam("name") String name,
			@ModelAttribute("network") Network network) {
		Node node = network.getNode(id);
		networkService.saveNode(node, name);
		return messageOnlySuccessResponse("Node saved.");
	}
}
