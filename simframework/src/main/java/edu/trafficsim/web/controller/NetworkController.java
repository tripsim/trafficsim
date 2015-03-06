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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.util.WktUtils;
import edu.trafficsim.web.Sequence;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.NetworkService;
import edu.trafficsim.web.service.entity.OdService;
import edu.trafficsim.web.service.entity.OsmImportService;
import edu.trafficsim.web.service.entity.OsmImportService.OsmHighwayValue;
import edu.trafficsim.web.service.entity.OsmImportService.OsmXapiUrl;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/network")
@SessionAttributes(value = { "sequence", "network", "odMatrix" })
public class NetworkController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	OdService odService;
	@Autowired
	OsmImportService extractOsmNetworkService;
	@Autowired
	MapJsonService mapJsonService;

	@Autowired
	TypesManager typesManager;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String networkView(@ModelAttribute("network") Network network,
			Model model) {
		model.addAttribute("linkCount", network.getLinks().size());
		model.addAttribute("nodeCount", network.getNodes().size());
		model.addAttribute("sourceCount", network.getSources().size());
		model.addAttribute("sinkCount", network.getSinks().size());
		return "components/network";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newNetworkView(Model model) {
		model.addAttribute("urls", OsmXapiUrl.values());
		model.addAttribute("options", OsmHighwayValue.values());
		return "components/network-new";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createNetwork(
			@ModelAttribute("sequence") Sequence sequence,
			@RequestParam("bbox") String bbox, @RequestParam("url") String url,
			@RequestParam("highway") String highway, Model model) {
		try {
			Network network = extractOsmNetworkService.createNetwork(url, bbox,
					highway, sequence);

			OdMatrix odMatrix = odService.createOdMatrix(sequence,
					network.getName());
			model.addAttribute("network", network);
			model.addAttribute("odMatrix", odMatrix);
			return successResponse("network created", "network/view",
					mapJsonService.getNetworkJson(network));
		} catch (Exception e) {
			return failureResponse(e);
		}
	}

	@RequestMapping(value = "/draw", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> breakLink(
			@RequestParam("startCoordX") Double startCoordX,
			@RequestParam("startCoordY") Double startCoordY,
			@RequestParam("endCoordX") Double endCoordX,
			@RequestParam("endCoordY") Double endCoordY,
			@RequestParam("startLink") Long startLinkId,
			@RequestParam("endLink") Long endLinkId,
			@RequestParam("startNode") Long startNodeId,
			@RequestParam("endNode") Long endNodeId,
			@RequestParam("linearGeom") String linearGeomWkt,
			@ModelAttribute("sequence") Sequence sequence,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {

		try {
			CoordinateSequence points = ((LineString) WktUtils
					.fromWKT(linearGeomWkt)).getCoordinateSequence();

			// get start node
			Node startNode;
			String nodeType = typesManager.getDefaultNodeTypeName();
			String linkType = typesManager.getDefaultLinkTypeName();
			if (startLinkId != null && startCoordX != null
					&& startCoordY != null) {
				Link link = network.getLink(startLinkId);
				startNode = networkService.breakLink(sequence, network,
						odMatrix, link, nodeType, startCoordX, startCoordY);
				points.setOrdinate(0, CoordinateSequence.X, startNode
						.getPoint().getX());
				points.setOrdinate(0, CoordinateSequence.Y, startNode
						.getPoint().getY());
			} else if (startNodeId != null) {
				startNode = network.getNode(startNodeId);
				points.setOrdinate(0, CoordinateSequence.X, startNode
						.getPoint().getX());
				points.setOrdinate(0, CoordinateSequence.Y, startNode
						.getPoint().getY());
			} else
				startNode = networkService.createNode(sequence, network,
						nodeType, points.getCoordinateCopy(0));
			if (startNode == null)
				return failureResponse("No starting node.");

			// get end node
			Node endNode;
			if (endLinkId != null && endCoordX != null && endCoordY != null) {
				Link link = network.getLink(endLinkId);
				endNode = networkService.breakLink(sequence, network, odMatrix,
						link, nodeType, endCoordX, endCoordY);
				points.setOrdinate(points.size() - 1, CoordinateSequence.X,
						endNode.getPoint().getX());
				points.setOrdinate(points.size() - 1, CoordinateSequence.Y,
						endNode.getPoint().getY());
			} else if (endNodeId != null) {
				endNode = network.getNode(endNodeId);
				points.setOrdinate(points.size() - 1, CoordinateSequence.X,
						endNode.getPoint().getX());
				points.setOrdinate(points.size() - 1, CoordinateSequence.Y,
						endNode.getPoint().getY());
			} else
				endNode = networkService.createNode(sequence, network,
						nodeType, points.getCoordinateCopy(points.size() - 1));
			if (endNode == null)
				return failureResponse("No ending node.");

			if (startNode.getLinkToNode(endNode) != null)
				return failureResponse("Link already exists.");
			Link link = networkService.createLink(sequence, network, linkType,
					startNode, endNode, points);
			if (startNode.getLinkFromNode(endNode) != null) {
				link.setReverseLink(startNode.getLinkFromNode(endNode));
			}

			return successResponse("Link(s) created.", null,
					mapJsonService.getNewLinkJson(link));

		} catch (ParseException e) {
			return failureResponse(e);
		}
	}
}
