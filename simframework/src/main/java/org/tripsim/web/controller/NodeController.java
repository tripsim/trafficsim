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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.web.service.entity.NetworkService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/node")
public class NodeController extends AbstractController {

	@Autowired
	NetworkService networkService;

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String nodeView(@PathVariable long id, Model model) {
		Network network = context.getNetwork();
		OdMatrix odMatrix = context.getOdMatrix();
		if (network == null)
			return "components/empty";
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		model.addAttribute("ods", odMatrix.getOdsFromNode(node.getId()));
		model.addAttribute("node", node);
		return "components/node";
	}

	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String linkInfo(@PathVariable long id, Model model) {
		Network network = context.getNetwork();
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		model.addAttribute("node", node);
		return "components/node :: info";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String linkEdit(@PathVariable long id, Model model) {
		Node node = context.getNetwork().getNode(id);
		if (node == null)
			return "components/empty";

		model.addAttribute("node", node);
		return "components/node-fragments :: form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveNodek(
			@RequestParam("id") long id, @RequestParam("desc") String desc) {
		networkService.saveNode(context.getNetwork(), id, desc);
		return successResponse("Node saved.");
	}
}
