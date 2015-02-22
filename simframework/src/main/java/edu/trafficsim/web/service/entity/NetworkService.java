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
package edu.trafficsim.web.service.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiValuedMap;
import edu.trafficsim.model.util.Coordinates;
import edu.trafficsim.web.Sequence;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("network-service")
public class NetworkService extends EntityService {

	private static final double DEFAULT_LANE_START = 10.0d;
	private static final double DEFAULT_LANE_END = -10.0d;
	private static final double DEFAULT_LANE_WIDTH = 4.0d;

	private static final String DEFAULT_NEW_NAME = "network";

	@Autowired
	NetworkFactory factory;
	@Autowired
	TypesManager typesManager;

	public Network createNetwork(Sequence sequence) {
		Network network = factory.createNetwork(sequence.nextId(),
				DEFAULT_NEW_NAME);
		network.setModified(true);
		return network;
	}

	public Node createNode(Sequence sequence, Network network, String nodeType,
			Coordinate coord) {
		Node node = factory.createNode(sequence.nextId(), DEFAULT_NEW_NAME,
				nodeType, coord);
		return node;
	}

	public Node saveNode(Network network, long nodeId, String name) {
		Node node = network.getNode(nodeId);
		if (node == null) {
			return null;
		}
		node.setName(name);
		network.setModified(true);
		return node;
	}

	public Link createLink(Sequence sequence, Network network, String linkType,
			Node startNode, Node endNode, CoordinateSequence points)
			throws ModelInputException {
		Link link = factory.createLink(sequence.nextId(), DEFAULT_NEW_NAME,
				linkType, startNode, endNode, points, null);
		network.add(link);
		return link;
	}

	public Node breakLink(Sequence sequence, Network network,
			OdMatrix odMatrix, Link link, String nodeType, double x, double y)
			throws ModelInputException {
		LineString[] linearGeoms = Coordinates.splitLinearGeom(
				link.getLinearGeom(), new Coordinate(x, y));

		// create new node
		Node newNode = factory.createNode(sequence.nextId(), DEFAULT_NEW_NAME,
				nodeType, new Coordinate(linearGeoms[0].getEndPoint()
						.getCoordinate()));

		Link newLink = breakLink(sequence, network, odMatrix, link, newNode,
				link.getLinkType(), linearGeoms);
		if (link.getReverseLink() != null) {
			Link newReverseLink = breakLink(sequence, network, odMatrix,
					link.getReverseLink(), newNode, link.getLinkType(),
					Coordinates.splitLinearGeom(link.getReverseLink()
							.getLinearGeom(), new Coordinate(x, y)));
			newLink.setReverseLink(link.getReverseLink());
			newReverseLink.setReverseLink(link);
		}

		network.setModified(true);
		return newNode;
	}

	private Link breakLink(Sequence sequence, Network network,
			OdMatrix odMatrix, Link link, Node newNode, String linkType,
			LineString[] linearGeoms) throws ModelInputException {

		// remove toConnectors from link
		MultiValuedMap<Integer, Lane> connectionMap = new MultiValuedMap<Integer, Lane>();
		for (ConnectionLane connector : link.getToConnectors()) {
			connectionMap.add(connector.getFromLane().getLaneId(),
					connector.getToLane());
			removeConnector(network, connector);
		}

		// set original link with new linear geometry
		// lane start, end will adjust in onGeomUpdate
		Node oldEndNode = link.getEndNode();
		link.setLinearGeom(link.getStartNode(), newNode, linearGeoms[0]);

		// create new link
		Link newLink = factory.createLink(sequence.nextId(), link.getName()
				+ " " + DEFAULT_NEW_NAME, linkType, newNode, oldEndNode,
				linearGeoms[1], link.getRoadInfo());
		network.add(newLink);
		// create new lanes
		Lane[] newLanes = factory.createLanes(
				sequence.nextIds(link.numOfLanes()), newLink,
				DEFAULT_LANE_START, DEFAULT_LANE_END, DEFAULT_LANE_WIDTH);
		// connect old lanes
		for (int i = 0; i < link.numOfLanes(); i++) {
			connectLanes(sequence, network, link.getLane(i), newLanes[i]);
		}
		for (Integer key : connectionMap.keys()) {
			for (Lane lane : connectionMap.get(key)) {
				connectLanes(sequence, network, newLanes[key], lane);
			}
		}

		// turn precentage
		odMatrix.updateFromLink(link, newLink);
		return newLink;
	}

	public Link saveLink(Network network, long linkId, String name,
			String highway, String roadName) {
		Link link = network.getLink(linkId);
		if (link == null) {
			return null;
		}
		link.setName(name);
		link.getRoadInfo().setHighway(highway);
		link.getRoadInfo().setName(roadName);
		network.setModified(true);
		return link;
	}

	protected void removeNodeOd(Network network, OdMatrix odMatrix, Node node,
			MultiValuedMap<String, String> map) {
		if (network.contains(node)) {
			return;
		}
		map.add("nodeIds", node.getId().toString());
	}

	public Map<String, Set<String>> removeLink(Network network,
			OdMatrix odMatrix, long id) throws ModelInputException {
		Link link = network.removeLink(id);
		MultiValuedMap<String, String> map = new MultiValuedMap<String, String>();
		if (!network.contains(link.getStartNode())) {
			map.add("nodeIds", link.getStartNode().getId().toString());
			odMatrix.onNodeRemoved(link.getStartNode());
		}
		if (!network.contains(link.getEndNode())) {
			map.add("nodeIds", link.getEndNode().getId().toString());
			odMatrix.onNodeRemoved(link.getEndNode());
		}

		odMatrix.removeTurnPercentage(link);
		return map.asMap();
	}

	public Link createReverseLink(Sequence sequence, Network network,
			long linkId) throws ModelInputException {
		Link link = network.getLink(linkId);
		if (link.getReverseLink() != null) {
			throw new RuntimeException("Reverse link already exists");
		}
		Link reverseLink = factory.createReverseLink(sequence.nextId(),
				link.getName() + " reverse", link);
		network.add(reverseLink);
		return reverseLink;
	}

	public Lane addLane(Sequence sequence, Network network, Link link)
			throws ModelInputException {
		Lane lane = factory.createLane(sequence.nextId(), link,
				DEFAULT_LANE_START, DEFAULT_LANE_END, DEFAULT_LANE_WIDTH);
		network.setModified(true);
		return lane;
	}

	public void removeLane(Network network, Link link, int laneId)
			throws ModelInputException {
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
		network.setModified(true);
	}

	public void saveLane(Network network, Lane lane, double start, double end,
			double width) throws ModelInputException {
		lane.setStart(start);
		lane.setEnd(end);
		lane.setWidth(width, true);
		network.setModified(true);
	}

	public ConnectionLane connectLanes(Sequence sequence, Network network,
			Lane laneFrom, Lane laneTo) throws ModelInputException {
		ConnectionLane connector = factory.connect(sequence.nextId(), laneFrom,
				laneTo, DEFAULT_LANE_WIDTH);
		network.setModified(true);
		return connector;
	}

	public void removeConnector(Network network, ConnectionLane connector) {
		connector.getNode().remove(connector);
		network.setModified(true);
	}

}
