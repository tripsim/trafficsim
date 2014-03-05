package edu.trafficsim.web.controller;

import java.util.Map;

import org.opengis.referencing.operation.TransformException;
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

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.NetworkService;
import edu.trafficsim.web.service.entity.OdService;
import edu.trafficsim.web.service.entity.OsmImportService;
import edu.trafficsim.web.service.entity.OsmImportService.OsmHighwayValue;

@Controller
@RequestMapping(value = "/network")
@SessionAttributes(value = { "sequence", "networkFactory", "odFactory",
		"network", "odMatrix" })
public class NetworkController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	OdService odService;
	@Autowired
	OsmImportService extractOsmNetworkService;
	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String networkView(@ModelAttribute("network") Network network,
			Model model) {
		if (network.isDirty())
			network.discover();

		model.addAttribute("linkCount", network.getLinks().size());
		model.addAttribute("nodeCount", network.getNodes().size());
		model.addAttribute("sourceCount", network.getSources().size());
		model.addAttribute("sinkCount", network.getSinks().size());
		return "components/network";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newNetworkView(Model model) {
		model.addAttribute("options", OsmHighwayValue.values());
		return "components/network-new";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createNetwork(@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("networkFactory") NetworkFactory factory,
			@ModelAttribute("odFactory") OdFactory odFactory,
			@RequestParam("bbox") String bbox,
			@RequestParam("highway") String highway, Model model) {
		try {
			Network network = extractOsmNetworkService.createNetwork(seq, bbox,
					highway, factory);

			OdMatrix odMatrix = odService.createOdMatrix(odFactory, seq);
			model.addAttribute("network", network);
			model.addAttribute("odMatrix", odMatrix);
			return successResponse("network created", "network/view",
					mapJsonService.getNetworkJson(network));
		} catch (Exception e) {
			return failureResponse(e);
		}
	}

	@RequestMapping(value = "/draw", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> breakLink(
			@RequestParam("startCoordX") Double startCoordX,
			@RequestParam("startCoordY") Double startCoordY,
			@RequestParam("endCoordX") Double endCoordX,
			@RequestParam("endCoordY") Double endCoordY,
			@RequestParam("startLink") Long startLinkId,
			@RequestParam("endLink") Long endLinkId,
			@RequestParam("startNode") Long startNodeId,
			@RequestParam("endNode") Long endNodeId,
			@RequestParam("linearGeom") String linearGeomWkt,
			@ModelAttribute("networkFactory") NetworkFactory factory,
			@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {

		try {
			CoordinateSequence points = ((LineString) MapJsonService.reader
					.read(linearGeomWkt)).getCoordinateSequence();

			// get start node
			Node startNode;
			if (startLinkId != null && startCoordX != null
					&& startCoordY != null) {
				Link link = network.getLink(startLinkId);
				startNode = networkService.breakLink(factory, seq, network,
						odMatrix, link, startCoordX, startCoordY);
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
				startNode = networkService.createNode(factory, seq, network,
						points.getCoordinateCopy(0));
			if (startNode == null)
				return failureResponse("No starting node.");

			// get end node
			Node endNode;
			if (endLinkId != null && endCoordX != null && endCoordY != null) {
				Link link = network.getLink(endLinkId);
				endNode = networkService.breakLink(factory, seq, network,
						odMatrix, link, endCoordX, endCoordY);
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
				endNode = networkService.createNode(factory, seq, network,
						points.getCoordinateCopy(points.size() - 1));
			if (endNode == null)
				return failureResponse("No ending node.");

			if (startNode.getToNode(endNode) != null)
				return failureResponse("Link already exists.");
			Link link = networkService.createLink(factory, seq, network,
					startNode, endNode, points);
			if (startNode.getFromNode(endNode) != null) {
				link.setReverseLink(startNode.getFromNode(endNode));
				networkService.shiftLanes(startNode.getFromNode(endNode));
			}

			return successResponse("Link(s) created.", null,
					mapJsonService.getNewLinkJson(link));

		} catch (TransformException e) {
			return failureResponse("Transformation issues!");
		} catch (ModelInputException e) {
			return failureResponse(e);
		} catch (ParseException e) {
			return failureResponse(e);
		}
	}
}
