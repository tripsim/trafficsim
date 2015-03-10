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
package org.tripsim.engine.network.osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.RoadInfo;
import org.tripsim.engine.network.NetworkExtractResult;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.engine.network.osm.Highways.OsmNode;
import org.tripsim.engine.network.osm.Highways.OsmWay;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.model.util.GeoReferencing;
import org.tripsim.model.util.GeoReferencing.TransformCoordinateFilter;
import org.tripsim.util.CoordinateTransformer;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class NetworkCreator {

	public static final long DEFAULT_START_ID = 1000000l;
	private static final TransformCoordinateFilter filter = GeoReferencing
			.getDefaultTransformFilter();

	public static NetworkExtractResult createNetwork(Highways highways,
			TypesManager typesManager, NetworkFactory networkFactory,
			String name) {
		NetworkCreator creator = new NetworkCreator(highways, typesManager,
				networkFactory, name);
		return creator.create();
	}

	final TypesManager typesManager;
	final NetworkFactory networkFactory;

	final Highways highways;
	final Network network;
	Map<OsmNode, Node> nodes;
	long id = DEFAULT_START_ID;

	NetworkCreator(Highways highways, TypesManager typesManager,
			NetworkFactory networkFactory, String name) {
		this.highways = highways;
		this.typesManager = typesManager;
		this.networkFactory = networkFactory;

		network = networkFactory.createNetwork(name);
		nodes = new HashMap<OsmNode, Node>(highways.getOsmNodes().size());
	}

	// TODO the osm nodes that has only two links if possible
	public NetworkExtractResult create() {
		NetworkExtractResult result = new NetworkExtractResult();
		result.setStartId(id);

		for (OsmWay osmWay : highways.getOsmWays()) {
			List<Coordinate> coords = new ArrayList<Coordinate>();

			int i = 0;
			// read the first osmnode, and add to coords
			OsmNode startOsmNode = osmWay.osmNodes.get(i);
			coords.add(startOsmNode.asCoord());
			OsmNode endOsmNode = null;
			RoadInfo roadInfo = networkFactory.createRoadInfo(id++, osmWay.id,
					osmWay.name, osmWay.highway);
			while (++i < osmWay.osmNodes.size()) {
				// read the next osmnode and add to coords until end
				endOsmNode = osmWay.osmNodes.get(i);
				coords.add(endOsmNode.asCoord());
				// if the osmnode is shared or at end, create a link based on
				// that coords, and clear the coords, make the endOsmNode as
				// start node for next link
				if (endOsmNode.isShared() || i == osmWay.osmNodes.size() - 1) {
					create(startOsmNode, endOsmNode, osmWay, coords, roadInfo,
							typesManager.getDefaultNodeTypeName(),
							typesManager.getDefaultLinkTypeName());
					coords.clear();
					startOsmNode = endOsmNode;
					coords.add(startOsmNode.asCoord());
				}
			}
		}

		CoordinateTransformer.transform(network, filter);

		result.setEndId(id);
		result.setObjectCount(result.getStartId() - result.getEndId());
		result.setNetwork(network);
		return result;
	}

	private void create(OsmNode startOsmNode, OsmNode endOsmNode,
			OsmWay osmWay, List<Coordinate> coords, RoadInfo roadInfo,
			String nodeType, String linkType) {
		Node startNode = nodes.get(startOsmNode);
		if (startNode == null) {
			startNode = createNode(startOsmNode, nodeType);
			nodes.put(startOsmNode, startNode);
		}
		Node endNode = nodes.get(endOsmNode);
		if (endNode == null) {
			endNode = createNode(endOsmNode, nodeType);
			nodes.put(endOsmNode, endNode);
		}

		Link link = createLink(osmWay, linkType, startNode, endNode, coords,
				roadInfo);
		network.add(link);
		if (!osmWay.oneway) {
			Link reverseLink = createReverseLink(link);
			reverseLink.setRoadInfo(roadInfo);
			network.add(reverseLink);
		}

	}

	private Node createNode(OsmNode osmNode, String nodeType) {
		String name = String.valueOf(osmNode.id);
		for (OsmWay osmWay : osmNode.osmWays) {
			name += " @ " + osmWay.name;
		}
		Node node = networkFactory
				.createNode(id++, nodeType, osmNode.asCoord());
		node.setDescription(name);
		// TODO edit node
		return node;
	}

	private Link createLink(OsmWay osmWay, String linkType, Node startNode,
			Node endNode, List<Coordinate> coords, RoadInfo roadInfo) {
		Link link = networkFactory.createLink(id++, linkType, startNode,
				endNode, coords.toArray(new Coordinate[0]), roadInfo);
		link.setDescription(osmWay.name);
		// TODO edit link
		return link;
	}

	private Link createReverseLink(Link link) {
		Link reverseLink = networkFactory.createReverseLink(id++, link);
		reverseLink.setDescription(String.format("%s Reversed",
				link.getDescription()));
		reverseLink.setRoadInfo(link.getRoadInfo());
		return reverseLink;
	}
}
