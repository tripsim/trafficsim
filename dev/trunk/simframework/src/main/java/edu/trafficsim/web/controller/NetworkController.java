package edu.trafficsim.web.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.NetworkService;
import edu.trafficsim.web.service.entity.OsmImportService;
import edu.trafficsim.web.service.entity.OsmImportService.OsmHighwayValue;

@Controller
@RequestMapping(value = "/network")
public class NetworkController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	OsmImportService extractOsmNetworkService;
	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String networkView(Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";

		if (network.isDirty())
			network.discover();
		model.addAttribute("network", network);
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
	Map<String, Object> createNetwork(@RequestParam("bbox") String bbox,
			@RequestParam("highway") String highway) {
		Network network;
		// TODO build feedbacks
		try {
			network = extractOsmNetworkService.createNetwork(bbox, highway,
					project.getNetworkFactory());
			project.setNetwork(network);
			return successResponse("network created", "network/view",
					mapJsonService.getNetworkJson(network));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModelInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return failureResponse("Network generation failed.");
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
			@RequestParam("linearGeom") String linearGeomWkt) {
		Network network = project.getNetwork();
		if (network == null)
			networkService.createNetwork();

		try {
			CoordinateSequence points = ((LineString) MapJsonService.reader
					.read(linearGeomWkt)).getCoordinateSequence();

			// get start node
			Node startNode;
			if (startLinkId != null && startCoordX != null
					&& startCoordY != null) {
				Link link = network.getLink(startLinkId);
				startNode = networkService.breakLink(link, startCoordX,
						startCoordY);
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
				startNode = networkService.createNode(points
						.getCoordinateCopy(0));
			if (startNode == null)
				return failureResponse("No starting node.");

			// get end node
			Node endNode;
			if (endLinkId != null && endCoordX != null && endCoordY != null) {
				Link link = network.getLink(endLinkId);
				endNode = networkService.breakLink(link, endCoordX, endCoordY);
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
				endNode = networkService.createNode(points
						.getCoordinateCopy(points.size() - 1));
			if (endNode == null)
				return failureResponse("No ending node.");

			if (startNode.getToNode(endNode) != null)
				return failureResponse("Link already exists.");
			Link link = networkService.createLink(startNode, endNode, points);
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
