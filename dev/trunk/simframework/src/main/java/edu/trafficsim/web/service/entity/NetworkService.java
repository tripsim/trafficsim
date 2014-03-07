package edu.trafficsim.web.service.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiValuedMap;
import edu.trafficsim.model.util.Coordinates;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class NetworkService extends EntityService {

	private static final double DEFAULT_LANE_START = 10.0d;
	private static final double DEFAULT_LANE_END = -10.0d;
	private static final double DEFAULT_LANE_WIDTH = 4.0d;

	private static final String DEFAULT_NEW_NAME = "New";

	public Network createNetwork(NetworkFactory factory, Sequence seq) {
		Network network = factory.createNetwork(seq, DEFAULT_NEW_NAME);
		return network;
	}

	public Node createNode(NetworkFactory factory, Sequence seq,
			Network network, NodeType nodeType, Coordinate coord) {
		Node node = factory.createNode(seq, DEFAULT_NEW_NAME, nodeType, coord);
		network.add(node);
		return node;
	}

	public void saveNode(Node node, String name) {
		node.setName(name);
	}

	public Link createLink(NetworkFactory factory, Sequence seq,
			Network network, LinkType linkType, Node startNode, Node endNode,
			CoordinateSequence points) throws ModelInputException,
			TransformException {
		Link link = factory.createLink(seq, DEFAULT_NEW_NAME, linkType,
				startNode, endNode, points, null);
		network.add(link);
		return link;
	}

	public Node breakLink(NetworkFactory factory, Sequence seq,
			Network network, OdMatrix odMatrix, Link link, NodeType nodeType,
			LinkType linkType, double x, double y) throws TransformException,
			ModelInputException {

		LineString[] linearGeoms = Coordinates.splitLinearGeom(
				link.getLinearGeom(), new Coordinate(x, y));

		// create new node
		Node newNode = factory.createNode(seq, DEFAULT_NEW_NAME, nodeType,
				new Coordinate(linearGeoms[0].getEndPoint().getCoordinate()));
		network.add(newNode);

		Link newLink = breakLink(factory, seq, network, odMatrix, link,
				newNode, linkType, linearGeoms);
		if (link.getReverseLink() != null) {
			Link newReverseLink = breakLink(factory, seq, network, odMatrix,
					link.getReverseLink(), newNode, linkType,
					Coordinates.splitLinearGeom(link.getReverseLink()
							.getLinearGeom(), new Coordinate(x, y)));
			newLink.setReverseLink(link.getReverseLink());
			newReverseLink.setReverseLink(link);
		}

		network.dirty();
		return newNode;
	}

	protected Link breakLink(NetworkFactory factory, Sequence seq,
			Network network, OdMatrix odMatrix, Link link, Node newNode,
			LinkType linkType, LineString[] linearGeoms)
			throws TransformException, ModelInputException {

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
		Link newLink = factory.createLink(seq, link.getName() + " "
				+ DEFAULT_NEW_NAME, linkType, newNode, oldEndNode,
				linearGeoms[1], link.getRoadInfo());
		network.add(newLink);
		// create new lanes
		Lane[] newLanes = factory.createLanes(seq, newLink, DEFAULT_LANE_START,
				DEFAULT_LANE_END, DEFAULT_LANE_WIDTH, link.numOfLanes());
		// connect old lanes
		for (int i = 0; i < link.numOfLanes(); i++)
			connectLanes(factory, seq, link.getLane(i), newLanes[i]);
		for (Integer key : connectionMap.keys()) {
			for (Lane lane : connectionMap.get(key)) {
				connectLanes(factory, seq, newLanes[key], lane);
			}
		}

		// update existing turnpercentages if any
		odMatrix.updateFromLink(link, newLink);
		return newLink;
	}

	public void saveLink(Link link, String name, String highway, String roadName) {
		link.setName(name);
		link.getRoadInfo().setHighway(highway);
		link.getRoadInfo().setName(roadName);
	}

	protected void removeNode(Network network, OdMatrix odMatrix, Node node,
			MultiValuedMap<String, String> map) {
		network.removeNode(node);
		odMatrix.remove(odMatrix.getOdsFromNode(node));
		odMatrix.remove(odMatrix.getOdsToNode(node));
		map.add("nodeIds", node.getId().toString());
	}

	public Map<String, Set<String>> removeLink(Network network,
			OdMatrix odMatrix, long id) throws TransformException {
		Link link = network.removeLink(id);
		Link reverse = link.getReverseLink();
		if (reverse != null) {
			reverse.setReverseLink(null);
			shiftLanes(reverse);
		}

		MultiValuedMap<String, String> map = new MultiValuedMap<String, String>();
		Node node = link.getStartNode();
		if (node.isEmpty()) {
			removeNode(network, odMatrix, node, map);
		} else {
			for (ConnectionLane connector : node.getOutConnectors(link)) {
				node.remove(connector);
			}
		}
		node = link.getEndNode();
		if (node.isEmpty()) {
			removeNode(network, odMatrix, node, map);
		} else {
			for (ConnectionLane connector : node.getInConnectors(link)) {
				node.remove(connector);
			}
		}

		odMatrix.removeTurnPercentage(link);

		network.dirty();
		return map.asMap();
	}

	public Link createReverseLink(NetworkFactory factory, Sequence seq,
			Network network, long id) throws UserInterfaceException,
			ModelInputException, TransformException {
		Link link = network.getLink(id);
		if (link.getReverseLink() != null)
			throw new UserInterfaceException("Reverse link already exists");
		Link reverseLink = factory.createReverseLink(seq, link.getName()
				+ " reverse", link);
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

	public Lane addLane(NetworkFactory factory, Sequence seq, Link link)
			throws ModelInputException, TransformException {
		return factory.createLane(seq, link, DEFAULT_LANE_START,
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

	public ConnectionLane connectLanes(NetworkFactory factory, Sequence seq,
			Lane laneFrom, Lane laneTo) throws ModelInputException,
			TransformException {
		return factory.connect(seq, laneFrom, laneTo, DEFAULT_LANE_WIDTH);
	}

	public void removeConnector(ConnectionLane connector) {
		connector.getNode().remove(connector);
	}

}
