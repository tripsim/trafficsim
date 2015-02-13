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

import org.opengis.referencing.operation.TransformException;
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

import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.Sequence;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.NetworkService;

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

		try {
			networkService.addLane(sequence, link);
		} catch (ModelInputException e) {
			return failureResponse(e.toString());
		} catch (TransformException e) {
			return failureResponse("Transform issues.");
		}

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

		try {
			networkService.removeLane(link, laneId);
		} catch (TransformException e) {
			return failureResponse("Transform issues.");
		}

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

		try {
			networkService.saveLane(lane, start, end, width);
		} catch (ModelInputException e) {
			return failureResponse(e.toString());
		} catch (TransformException e) {
			return failureResponse("Transform issues.");
		}

		return laneUpdatedResponse(network, id);
	}

	private Map<String, Object> laneUpdatedResponse(Network network, long linkId) {
		return successResponse("lane updated.", null,
				mapJsonService.getLanesConnectorsJson(network, linkId));
	}

}
