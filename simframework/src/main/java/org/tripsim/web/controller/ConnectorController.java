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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tripsim.api.model.Connector;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.web.service.MapJsonService;
import org.tripsim.web.service.entity.NetworkService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/connector")
public class ConnectorController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	MapJsonService mapJsonService;

	@Autowired
	NetworkFactory networkFactory;

	@RequestMapping(value = "view/{fromLinkId}/{toLinkId}", method = RequestMethod.GET)
	public String connectorView(
			@PathVariable long fromLinkId,
			@MatrixVariable(value = "lanePosition", pathVar = "fromLinkId") int fromlanePosition,
			@PathVariable long toLinkId,
			@MatrixVariable(value = "lanePosition", pathVar = "toLinkId") int tolanePosition,
			Model model) {
		Network network = context.getNetwork();
		Link fromLink = network.getLink(fromLinkId);
		if (fromLink == null)
			return "components/empty";
		Lane fromLane = fromLink.getLane(fromlanePosition);
		if (fromLane == null)
			return "components/empty";
		Link toLink = network.getLink(toLinkId);
		if (toLink == null)
			return "components/empty";
		Lane toLane = toLink.getLane(tolanePosition);
		if (toLane == null)
			return "components/empty";

		Connector connector = fromLane.getLink().getEndNode()
				.getConnector(fromLane, toLane);
		if (connector == null)
			return "components/empty";

		model.addAttribute("connector", connector);
		return "components/connector";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> connectLanes(
			@RequestParam("link1") long link1Id,
			@RequestParam("lane1") int lane1Id,
			@RequestParam("link2") long link2Id,
			@RequestParam("lane2") int lane2Id) {
		Network network = context.getNetwork();
		Link link1 = network.getLink(link1Id);
		if (link1 == null)
			return failureResponse("no link.");
		Lane lane1 = link1.getLane(lane1Id);
		if (lane1 == null)
			return failureResponse("no lane.");
		Link link2 = network.getLink(link2Id);
		if (link2 == null)
			return failureResponse("no link.");
		Lane lane2 = link2.getLane(lane2Id);
		if (lane2 == null)
			return failureResponse("no lane.");

		if (link1.getEndNode() == link2.getStartNode()) {
			if (link1.getEndNode().isConnected(lane1, lane2)) {
				return failureResponse("already connected");
			}
			Connector connector = networkService.connectLanes(
					context.getSequence(), network, lane1, lane2);
			return connectSuccessResponse(connector, "Success 1!");
		} else if (link1.getStartNode() == link2.getEndNode()) {
			if (link1.getStartNode().isConnected(lane2, lane1)) {
				return failureResponse("already connected");
			}
			Connector connector = networkService.connectLanes(
					context.getSequence(), network, lane2, lane1);
			return connectSuccessResponse(connector, "Success 2!");
		} else {
			return failureResponse("no connection");
		}
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeConnector(
			@RequestParam("fromLink") long fromLinkId,
			@RequestParam("fromLane") int fromlanePosition,
			@RequestParam("toLink") long toLinkId,
			@RequestParam("toLane") int tolanePosition) {
		Network network = context.getNetwork();
		Link fromLink = network.getLink(fromLinkId);
		if (fromLink == null)
			return failureResponse("no link.");
		Lane fromLane = fromLink.getLane(fromlanePosition);
		if (fromLane == null)
			return failureResponse("no lane.");
		Link toLink = network.getLink(toLinkId);
		if (toLink == null)
			return failureResponse("no link.");
		Lane toLane = toLink.getLane(tolanePosition);
		if (toLane == null)
			return failureResponse("no lane.");

		Connector connector = fromLane.getLink().getEndNode()
				.getConnector(fromLane, toLane);
		if (connector == null)
			return failureResponse("no lane.");

		networkService.removeConnector(network, connector);
		return disconnectSuccessResponse(connector, "Success!");
	}

	private Map<String, Object> connectSuccessResponse(Connector connector,
			String message) {
		return successResponse(message, null,
				mapJsonService.getConnectorJson(connector));
	}

	private Map<String, Object> disconnectSuccessResponse(Connector connector,
			String message) {
		return successResponse(message, null,
				mapJsonService.getConnectorsIdsJson(connector));
	}
}
