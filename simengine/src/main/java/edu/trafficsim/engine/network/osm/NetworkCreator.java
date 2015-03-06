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
package edu.trafficsim.engine.network.osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.RoadInfo;
import edu.trafficsim.engine.network.NetworkExtractResult;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.network.osm.Highways.OsmNode;
import edu.trafficsim.engine.network.osm.Highways.OsmWay;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.util.GeoReferencing;
import edu.trafficsim.model.util.GeoReferencing.TransformCoordinateFilter;
import edu.trafficsim.util.CoordinateTransformer;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class NetworkCreator {

	public static final long DEFAULT_START_ID = 1000000l;
	private static final TransformCoordinateFilter filter = GeoReferencing
			.getDefaultTransformFilter();

	// TODO the osm nodes that has only two links if possible
	public static NetworkExtractResult createNetwork(Highways highways,
			TypesManager typesManager, NetworkFactory networkFactory,
			String name) {
		NetworkExtractResult result = new NetworkExtractResult();
		long id = DEFAULT_START_ID;
		result.setStartId(id);

		Network network = networkFactory.createNetwork(name);
		Map<OsmNode, Node> nodes = new HashMap<OsmNode, Node>(highways
				.getOsmNodes().size());
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
					create(network, nodes, startOsmNode, endOsmNode, osmWay,
							coords, roadInfo, networkFactory,
							typesManager.getDefaultNodeTypeName(),
							typesManager.getDefaultLinkTypeName(), id);
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

	private static void create(Network network, Map<OsmNode, Node> nodes,
			OsmNode startOsmNode, OsmNode endOsmNode, OsmWay osmWay,
			List<Coordinate> coords, RoadInfo roadInfo,
			NetworkFactory networkFactory, String nodeType, String linkType,
			long id) {
		Node startNode = nodes.get(startOsmNode);
		if (startNode == null) {
			startNode = createNode(startOsmNode, nodeType, networkFactory, id);
			nodes.put(startOsmNode, startNode);
		}
		Node endNode = nodes.get(endOsmNode);
		if (endNode == null) {
			endNode = createNode(endOsmNode, nodeType, networkFactory, id);
			nodes.put(endOsmNode, endNode);
		}

		Link link = createLink(osmWay, linkType, startNode, endNode, coords,
				roadInfo, networkFactory, id);
		network.add(link);
		if (!osmWay.oneway) {
			Link reverseLink = createReverseLink(link, networkFactory, id);
			reverseLink.setRoadInfo(roadInfo);
			network.add(reverseLink);
		}

	}

	private static Node createNode(OsmNode osmNode, String nodeType,
			NetworkFactory networkFactory, long id) {
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

	private static Link createLink(OsmWay osmWay, String linkType,
			Node startNode, Node endNode, List<Coordinate> coords,
			RoadInfo roadInfo, NetworkFactory networkFactory, long id) {
		Link link = networkFactory.createLink(id++, linkType, startNode,
				endNode, coords.toArray(new Coordinate[0]), roadInfo);
		link.setDescription(osmWay.name);
		// TODO edit link
		return link;
	}

	private static Link createReverseLink(Link link,
			NetworkFactory networkFactory, long id) {
		Link reverseLink = networkFactory.createReverseLink(id++, link);
		reverseLink.setDescription(String.format("%s Reversed",
				link.getDescription()));
		reverseLink.setRoadInfo(link.getRoadInfo());
		return reverseLink;
	}
}
