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
package edu.trafficsim.engine.osm.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.engine.osm.parser.Highways.OsmNode;
import edu.trafficsim.engine.osm.parser.Highways.OsmWay;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.util.GeoReferencing;
import edu.trafficsim.model.util.GeoReferencing.TransformCoordinateFilter;
import edu.trafficsim.utility.CoordinateTransformer;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class NetworkCreator {

	private static final TransformCoordinateFilter filter = GeoReferencing
			.getDefaultTransformFilter();

	// TODO the osm nodes that has only two links if possible
	public static Network createNetwork(Highways highways,
			TypesLibrary typesLibrary, NetworkFactory networkFactory,
			Sequence seq, String name) throws ModelInputException,
			TransformException {
		Network network = networkFactory.createNetwork(seq, name);

		Map<OsmNode, Node> nodes = new HashMap<OsmNode, Node>(highways
				.getOsmNodes().size());
		for (OsmWay osmWay : highways.getOsmWays()) {
			List<Coordinate> coords = new ArrayList<Coordinate>();

			int i = 0;
			// read the first osmnode, and add to coords
			OsmNode startOsmNode = osmWay.osmNodes.get(i);
			coords.add(startOsmNode.asCoord());
			OsmNode endOsmNode = null;
			RoadInfo roadInfo = networkFactory.createRoadInfo(seq, osmWay.name,
					osmWay.id, osmWay.highway);
			while (++i < osmWay.osmNodes.size()) {
				// read the next osmnode and add to coords until end
				endOsmNode = osmWay.osmNodes.get(i);
				coords.add(endOsmNode.asCoord());
				// if the osmnode is shared or at end, create a link based on
				// that coords, and clear the coords, make the endOsmNode as
				// start node for next link
				if (endOsmNode.isShared() || i == osmWay.osmNodes.size() - 1) {
					create(network, nodes, startOsmNode, endOsmNode, osmWay,
							coords, roadInfo, networkFactory, seq,
							typesLibrary.getDefaultNodeType(),
							typesLibrary.getDefaultLinkType());
					coords.clear();
					startOsmNode = endOsmNode;
					coords.add(startOsmNode.asCoord());
				}
			}
		}

		CoordinateTransformer.transform(network, filter);
		network.discover();
		return network;
	}

	private static void create(Network network, Map<OsmNode, Node> nodes,
			OsmNode startOsmNode, OsmNode endOsmNode, OsmWay osmWay,
			List<Coordinate> coords, RoadInfo roadInfo,
			NetworkFactory networkFactory, Sequence seq, NodeType nodeType,
			LinkType linkType) throws ModelInputException, TransformException {
		Node startNode = nodes.get(startOsmNode);
		if (startNode == null) {
			startNode = createNode(startOsmNode, nodeType, networkFactory, seq);
			network.add(startNode);
			nodes.put(startOsmNode, startNode);
		}
		Node endNode = nodes.get(endOsmNode);
		if (endNode == null) {
			endNode = createNode(endOsmNode, nodeType, networkFactory, seq);
			network.add(endNode);
			nodes.put(endOsmNode, endNode);
		}

		Link link = createLink(osmWay, linkType, startNode, endNode, coords,
				roadInfo, networkFactory, seq);
		network.add(link);
		if (!osmWay.oneway) {
			Link reverseLink = createReverseLink(link, networkFactory, seq);
			reverseLink.setRoadInfo(roadInfo);
			network.add(reverseLink);
		}

	}

	private static Node createNode(OsmNode osmNode, NodeType nodeType,
			NetworkFactory networkFactory, Sequence seq) {
		String name = String.valueOf(osmNode.id);
		for (OsmWay osmWay : osmNode.osmWays) {
			name += " @ " + osmWay.name;
		}
		Node node = networkFactory.createNode(seq, name, nodeType,
				osmNode.asCoord());
		// TODO edit node
		return node;
	}

	private static Link createLink(OsmWay osmWay, LinkType linkType,
			Node startNode, Node endNode, List<Coordinate> coords,
			RoadInfo roadInfo, NetworkFactory networkFactory, Sequence seq)
			throws ModelInputException, TransformException {
		Link link = networkFactory
				.createLink(seq, osmWay.name, linkType, startNode, endNode,
						coords.toArray(new Coordinate[0]), roadInfo);
		// TODO edit link
		return link;
	}

	private static Link createReverseLink(Link link,
			NetworkFactory networkFactory, Sequence seq)
			throws ModelInputException, TransformException {
		Link reverseLink = networkFactory.createReverseLink(seq,
				String.format("%s Reversed", link.getName()), link);
		reverseLink.setRoadInfo(link.getRoadInfo());
		return reverseLink;
	}
}
