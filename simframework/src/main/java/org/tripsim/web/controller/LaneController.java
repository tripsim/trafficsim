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
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.web.Sequence;
import org.tripsim.web.service.MapJsonService;
import org.tripsim.web.service.entity.NetworkService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/lane")
@SessionAttributes(value = { "sequence", "network" })
public class LaneController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	MapJsonService mapJsonService;
	@Autowired
	NetworkFactory factory;

	@RequestMapping(value = "/{linkId}", method = RequestMethod.GET)
	public String lanes(@PathVariable long linkId,
			@ModelAttribute("network") Network network, Model model) {
		Link link = network.getLink(linkId);
		if (link == null)
			return "components/empty";

		model.addAttribute("link", link);
		return "components/link :: lanes";
	}

	@RequestMapping(value = "/info/{linkId}", method = RequestMethod.GET)
	public String laneInfo(@PathVariable long linkId,
			@MatrixVariable int laneId,
			@ModelAttribute("network") Network network, Model model) {
		Link link = network.getLink(linkId);
		if (link == null)
			return "components/empty";
		Lane lane = link.getLane(laneId);

		model.addAttribute("lane", lane);
		return "components/lane-fragments :: info";
	}

	@RequestMapping(value = "/form/{linkId}", method = RequestMethod.GET)
	public String laneForm(
			@PathVariable long linkId,
			@MatrixVariable int laneId,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			@ModelAttribute("network") Network network, Model model) {
		Link link = network.getLink(linkId);
		if (link == null)
			return "components/empty";
		Lane lane = link.getLane(laneId);

		model.addAttribute("lane", lane);
		model.addAttribute("isNew", isNew);
		return "components/lane-fragments :: form";
	}

	@RequestMapping(value = "/addtolink", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addLane(
			@RequestParam("id") long id,
			@ModelAttribute("sequence") Sequence sequence,
			@ModelAttribute("network") Network network) {
		Link link = network.getLink(id);
		if (link == null) {
			return failureResponse("link doesn't exist.");
		}

		networkService.addLane(sequence, network, link);
		return laneUpdatedResponse(network, id);
	}

	@RequestMapping(value = "/removefromlink", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeLane(
			@RequestParam("laneId") int laneId,
			@RequestParam("linkId") long linkId,
			@ModelAttribute("network") Network network) {

		Link link = network.getLink(linkId);
		if (link == null) {
			return failureResponse("link doesn't exist.");
		}

		networkService.removeLane(network, link, laneId);

		return laneUpdatedResponse(network, linkId);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveLane(
			@RequestParam("linkId") long id,
			@RequestParam("laneId") int laneId,
			@RequestParam("start") double start,
			@RequestParam("end") double end,
			@RequestParam("width") double width,
			@ModelAttribute("network") Network network) {
		Link link = network.getLink(id);
		if (link == null) {
			return failureResponse("link doesn't exist.");
		}
		Lane lane = link.getLane(laneId);
		if (lane == null)
			return failureResponse("lane doesn't exist.");

		networkService.saveLane(network, lane, start, end, width);
		return laneUpdatedResponse(network, id);
	}

	private Map<String, Object> laneUpdatedResponse(Network network, long linkId) {
		return successResponse("lane updated.", null,
				mapJsonService.getLanesConnectorsJson(network, linkId));
	}

}
