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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.api.model.Connector;
import edu.trafficsim.api.model.Lane;
import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.util.Coordinates;
import edu.trafficsim.util.MultiValuedMap;
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

	public Network createNetwork() {
		Network network = factory.createNetwork(DEFAULT_NEW_NAME);
		network.setModified(true);
		return network;
	}

	public Node createNode(Sequence sequence, Network network, String nodeType,
			Coordinate coord) {
		Node node = factory.createNode(sequence.nextId(), nodeType, coord);
		return node;
	}

	public Node saveNode(Network network, long nodeId, String description) {
		Node node = network.getNode(nodeId);
		if (node == null) {
			return null;
		}
		node.setDescription(description);
		network.setModified(true);
		return node;
	}

	public Link createLink(Sequence sequence, Network network, String linkType,
			Node startNode, Node endNode, CoordinateSequence points) {
		Link link = factory.createLink(sequence.nextId(), linkType, startNode,
				endNode, points, null);
		network.add(link);
		return link;
	}

	public Node breakLink(Sequence sequence, Network network,
			OdMatrix odMatrix, Link link, String nodeType, double x, double y) {
		LineString[] linearGeoms = Coordinates.splitLinearGeom(
				link.getLinearGeom(), new Coordinate(x, y));

		// create new node
		Node newNode = factory.createNode(sequence.nextId(), nodeType,
				new Coordinate(linearGeoms[0].getEndPoint().getCoordinate()));

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
			LineString[] linearGeoms) {

		// remove out going Connectors from link
		MultiValuedMap<Integer, Lane> connectionMap = new MultiValuedMap<Integer, Lane>();
		for (Connector connector : link.getOutConnectors()) {
			connectionMap.add(connector.getFromLane().getLanePosition(),
					connector.getToLane());
			removeConnector(network, connector);
		}

		// set original link with new linear geometry
		// lane start, end will adjust in onGeomUpdate
		Node oldEndNode = link.getEndNode();
		link.update(link.getStartNode(), newNode, linearGeoms[0]);

		// create new link
		Link newLink = factory.createLink(sequence.nextId(), linkType, newNode,
				oldEndNode, linearGeoms[1], link.getRoadInfo());
		newLink.setDescription(link.getDescription() + " broken!");
		network.add(newLink);
		// create new lanes
		List<Lane> newLanes = factory.createLanes(
				sequence.nextIds(link.numOfLanes()), newLink,
				DEFAULT_LANE_START, DEFAULT_LANE_END, DEFAULT_LANE_WIDTH);
		// connect old lanes
		for (int i = 0; i < link.numOfLanes(); i++) {
			connectLanes(sequence, network, link.getLane(i), newLanes.get(i));
		}
		for (Integer key : connectionMap.keySet()) {
			for (Lane lane : connectionMap.get(key)) {
				connectLanes(sequence, network, newLanes.get(key), lane);
			}
		}

		// turn precentage
		odMatrix.updateFromLink(link, newLink);
		return newLink;
	}

	public Link saveLink(Network network, long linkId, String description,
			String highway, String roadName) {
		Link link = network.getLink(linkId);
		if (link == null) {
			return null;
		}
		link.setDescription(description);
		link.getRoadInfo().setRoadName(roadName);
		link.getRoadInfo().setHighway(highway);
		network.setModified(true);
		return link;
	}

	protected void removeNodeOd(Network network, OdMatrix odMatrix, Node node,
			MultiValuedMap<String, String> map) {
		if (network.contains(node)) {
			return;
		}
		map.add("nodeIds", String.valueOf(node.getId()));
	}

	public Map<String, Collection<String>> removeLink(Network network,
			OdMatrix odMatrix, long id) {
		Link link = network.removeLink(id);
		MultiValuedMap<String, String> map = new MultiValuedMap<String, String>();
		if (!network.contains(link.getStartNode())) {
			map.add("nodeIds", String.valueOf(link.getStartNode().getId()));
			odMatrix.onNodeRemoved(link.getStartNode());
		}
		if (!network.contains(link.getEndNode())) {
			map.add("nodeIds", String.valueOf(link.getEndNode().getId()));
			odMatrix.onNodeRemoved(link.getEndNode());
		}

		odMatrix.removeTurnPercentage(link);
		return map.asMap();
	}

	public Link createReverseLink(Sequence sequence, Network network,
			long linkId) {
		Link link = network.getLink(linkId);
		if (link.getReverseLink() != null) {
			throw new RuntimeException("Reverse link already exists");
		}
		Link reverseLink = factory.createReverseLink(sequence.nextId(), link);
		reverseLink.setDescription(link.getDescription() + " reverse");
		network.add(reverseLink);
		return reverseLink;
	}

	public Lane addLane(Sequence sequence, Network network, Link link) {
		Lane lane = factory.createLane(sequence.nextId(), link,
				DEFAULT_LANE_START, DEFAULT_LANE_END, DEFAULT_LANE_WIDTH);
		network.setModified(true);
		return lane;
	}

	public void removeLane(Network network, Link link, int laneId) {
		link.remove(laneId);
		network.setModified(true);
	}

	public void saveLane(Network network, Lane lane, double start, double end,
			double width) {
		lane.setStartOffset(start);
		lane.setEndOffset(end);
		lane.setWidth(width, true);
		network.setModified(true);
	}

	public Connector connectLanes(Sequence sequence, Network network,
			Lane laneFrom, Lane laneTo) {
		Connector connector = factory.connect(sequence.nextId(), laneFrom,
				laneTo);
		network.setModified(true);
		return connector;
	}

	public void removeConnector(Network network, Connector connector) {
		connector.getNode().remove(connector);
		network.setModified(true);
	}

}
