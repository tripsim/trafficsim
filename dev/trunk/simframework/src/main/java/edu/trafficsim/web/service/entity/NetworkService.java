package edu.trafficsim.web.service.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiValuedMap;
import edu.trafficsim.model.util.Coordinates;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class NetworkService extends EntityService {

	@Autowired
	SimulationProject project;

	private static final double DEFAULT_LANE_START = 10.0d;
	private static final double DEFAULT_LANE_END = -10.0d;
	private static final double DEFAULT_LANE_WIDTH = 4.0d;

	private static final String DEFAULT_NEW_NAME = "New";

	public Network createNetwork() {
		Network network = project.getNetworkFactory().createNetwork(
				project.nextSeq(), DEFAULT_NEW_NAME);
		project.setNetwork(network);
		return network;
	}

	public Node createNode(Coordinate coord) {
		Node node = project.getNetworkFactory().createNode(project.nextSeq(),
				DEFAULT_NEW_NAME, coord);
		project.getNetwork().add(node);
		return node;
	}

	public Link createLink(Node startNode, Node endNode,
			CoordinateSequence points) throws ModelInputException,
			TransformException {
		Link link = project.getNetworkFactory().createLink(project.nextSeq(),
				DEFAULT_NEW_NAME, startNode, endNode, points);
		project.getNetwork().add(link);
		return link;
	}

	public Node breakLink(Link link, double x, double y)
			throws TransformException, ModelInputException {
		NetworkFactory factory = project.getNetworkFactory();

		LineString[] linearGeoms = Coordinates.splitLinearGeom(
				link.getLinearGeom(), new Coordinate(x, y));

		// create new node
		Node newNode = factory.createNode(project.nextSeq(), DEFAULT_NEW_NAME,
				new Coordinate(linearGeoms[0].getEndPoint().getCoordinate()));
		project.getNetwork().add(newNode);

		Link newLink = breakLink(link, newNode, linearGeoms);
		if (link.getReverseLink() != null) {
			Link newReverseLink = breakLink(link.getReverseLink(), newNode,
					Coordinates.splitLinearGeom(link.getReverseLink()
							.getLinearGeom(), new Coordinate(x, y)));
			newLink.setReverseLink(link.getReverseLink());
			newReverseLink.setReverseLink(link);
		}

		project.getNetwork().dirty();
		return newNode;
	}

	protected Link breakLink(Link link, Node newNode, LineString[] linearGeoms)
			throws TransformException, ModelInputException {
		NetworkFactory factory = project.getNetworkFactory();

		// remove toConnectors from link
		MultiValuedMap<Integer, Lane> connectionMap = new MultiValuedMap<Integer, Lane>();
		for (ConnectionLane connector : link.getToConnectors()) {
			connectionMap.add(connector.getFromLane().getLaneId(),
					connector.getToLane());
			removeConnector(connector);
		}

		// set original link with new linear geometry
		// lane start, end will adjust in onGeomUpdate
		Node oldEndNode = link.getEndNode();
		link.setLinearGeom(link.getStartNode(), newNode, linearGeoms[0]);

		// create new link
		Link newLink = factory.createLink(project.nextSeq(), link.getName()
				+ " " + DEFAULT_NEW_NAME, newNode, oldEndNode, linearGeoms[1]);
		project.getNetwork().add(newLink);
		// create new lanes
		Lane[] newLanes = factory.createLanes(
				project.nextSeqs(link.numOfLanes()), newLink,
				DEFAULT_LANE_START, DEFAULT_LANE_END, DEFAULT_LANE_WIDTH);
		// connect old lanes
		for (int i = 0; i < link.numOfLanes(); i++)
			connectLanes(link.getLane(i), newLanes[i]);
		for (Integer key : connectionMap.keys()) {
			for (Lane lane : connectionMap.get(key)) {
				connectLanes(link.getLane(key), lane);
			}
		}

		// update existing turnpercentages if any
		project.getOdMatrix().updateFromLink(link, newLink);
		return newLink;
	}

	public void saveLink(Link link, String name, String highway, String roadName) {
		link.setName(name);
		link.getRoadInfo().setHighway(highway);
		link.getRoadInfo().setRoadName(roadName);
	}

	protected void removeNode(Node node, MultiValuedMap<String, String> map) {
		Network network = project.getNetwork();
		OdMatrix odMatrix = project.getOdMatrix();
		network.removeNode(node);
		odMatrix.remove(odMatrix.getOdsFromNode(node));
		odMatrix.remove(odMatrix.getOdsToNode(node));
		map.add("nodeIds", node.getId().toString());
	}

	public Map<String, Set<String>> removeLink(long id)
			throws TransformException {
		Network network = project.getNetwork();
		Link link = network.removeLink(id);
		Link reverse = link.getReverseLink();
		if (reverse != null) {
			reverse.setReverseLink(null);
			shiftLanes(reverse);
		}

		MultiValuedMap<String, String> map = new MultiValuedMap<String, String>();
		Node node = link.getStartNode();
		if (node.isEmpty()) {
			removeNode(node, map);
		} else {
			for (ConnectionLane connector : node.getOutConnectors(link)) {
				node.remove(connector);
			}
		}
		node = link.getEndNode();
		if (node.isEmpty()) {
			removeNode(node, map);
		} else {
			for (ConnectionLane connector : node.getInConnectors(link)) {
				node.remove(connector);
			}
		}

		project.getOdMatrix().removeTurnPercentage(link);

		network.dirty();
		return map.asMap();
	}

	public Link createReverseLink(long id) throws UserInterfaceException,
			ModelInputException, TransformException {
		Network network = project.getNetwork();
		Link link = network.getLink(id);
		if (link.getReverseLink() != null)
			throw new UserInterfaceException("Reverse link already exists");
		Link reverseLink = project.getNetworkFactory().createReverseLink(
				project.nextSeq(), link.getName() + " reverse", link);
		network.add(reverseLink);
		shiftLanes(link);
		return reverseLink;
	}

	public void shiftLanes(Link link) throws TransformException {
		double offset = link.getWidth() / 2;
		if (link.getReverseLink() == null) {
			for (Lane lane : link.getLanes())
				lane.setShift(lane.getShift() - offset, false);
		} else {
			for (Lane lane : link.getLanes())
				lane.setShift(lane.getShift() + offset, false);
		}
	}

	public Lane addLane(Link link) throws ModelInputException,
			TransformException {
		NetworkFactory factory = project.getNetworkFactory();
		return factory.createLane(project.nextSeq(), link, DEFAULT_LANE_START,
				DEFAULT_LANE_END, DEFAULT_LANE_WIDTH);
	}

	public void removeLane(Link link, int laneId) throws TransformException {
		Lane lane = link.getLane(laneId);
		List<ConnectionLane> toRemove = new ArrayList<ConnectionLane>();
		for (ConnectionLane connector : link.getStartNode().getOutConnectors(
				lane)) {
			toRemove.add(connector);
		}
		for (ConnectionLane connector : link.getEndNode().getInConnectors(lane)) {
			toRemove.add(connector);
		}
		for (ConnectionLane connector : toRemove) {
			connector.getNode().remove(connector);
		}
		link.remove(laneId);
	}

	public void saveLane(Lane lane, double start, double end, double width)
			throws TransformException, ModelInputException {
		lane.setStart(start);
		lane.setEnd(end);
		lane.setWidth(width, true);
	}

	public ConnectionLane connectLanes(Lane laneFrom, Lane laneTo)
			throws ModelInputException, TransformException {
		return project.getNetworkFactory().connect(project.nextSeq(), laneFrom,
				laneTo, DEFAULT_LANE_WIDTH);
	}

	public void removeConnector(ConnectionLane connector) {
		connector.getNode().remove(connector);
	}

}
