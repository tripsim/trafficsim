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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.web.service.MapJsonService;
import org.tripsim.web.service.OsmImportService.OsmHighwayValue;
import org.tripsim.web.service.entity.NetworkService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/link")
public class LinkController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	MapJsonService mapJsonService;

	@Autowired
	NetworkFactory networkFactory;

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String linkView(@PathVariable long id, Model model) {
		Link link = context.getNetwork().getLink(id);
		if (link == null)
			return "components/empty";

		List<Link> links = new ArrayList<Link>(2);
		links.add(link);
		if (link.getReverseLink() != null)
			links.add(link.getReverseLink());

		model.addAttribute("links", links);
		return "components/link";
	}

	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String linkInfo(@PathVariable long id, Model model) {
		Link link = context.getNetwork().getLink(id);
		if (link == null)
			return "components/empty";

		model.addAttribute("link", link);
		return "components/link :: info";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String linkEdit(@PathVariable long id, Model model) {
		Link link = context.getNetwork().getLink(id);
		if (link == null)
			return "components/empty";

		model.addAttribute("highways", OsmHighwayValue.values());
		model.addAttribute("link", link);
		return "components/link-fragments :: form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveLink(
			@RequestParam("id") long id, @RequestParam("desc") String desc,
			@RequestParam("highway") String highway,
			@RequestParam("roadName") String roadName) {
		networkService.saveLink(context.getNetwork(), id, desc, highway,
				roadName);
		return successResponse("Link saved.");
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeLink(
			@RequestParam("id") long id) {
		Network network = context.getNetwork();
		OdMatrix odMatrix = context.getOdMatrix();
		Link reverse = network.getLink(id).getReverseLink();
		Map<String, Object> data = new HashMap<String, Object>();
		for (Map.Entry<String, Collection<String>> entry : networkService
				.removeLink(network, odMatrix, id).entrySet()) {
			data.put(entry.getKey(), entry.getValue());
		}
		if (reverse != null) {
			data.put(
					"lanesconnectors",
					mapJsonService.getLanesConnectorsJson(network,
							reverse.getId()));
		} else {
			data.put("lanesconnectors",
					mapJsonService.getEmptyLanesConnectorsJson());
		}
		return successResponse("Link removed.", null, data);
	}

	@RequestMapping(value = "/createreverse", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createReverseLink(
			@RequestParam("id") long id) {
		Network network = context.getNetwork();
		Link reverse = networkService.createReverseLink(context.getSequence(),
				network, id);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("link", mapJsonService.getLinkJson(network, reverse.getId()));
		data.put("lanesconnectors",
				mapJsonService.getLanesConnectorsJson(network, id));
		return successResponse("Link removed.", "link/view/" + id, data);
	}
}
