package edu.trafficsim.engine.osm;

import java.io.IOException;
import java.io.Reader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonParseException;
import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.engine.osm.Highways.OsmNode;
import edu.trafficsim.engine.osm.Highways.OsmWay;
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

public class OsmNetworkExtractor {

	private static final String NETWORK_NAME = "New Network";
	private static final TransformCoordinateFilter filter = GeoReferencing
			.getDefaultTransformFilter();

	private Sequence seq;

	public OsmNetworkExtractor(Sequence seq) {
		this.seq = seq;
	}

	public Highways parse(Reader reader) throws JsonParseException, IOException {
		HighwaysJsonParser parser = new HighwaysJsonParser();
		parser.parse(reader);
		return parser.getParsedHighways();
	}

	public Highways parse(String urlStr) throws ModelInputException,
			JsonParseException, IOException {
		HighwaysJsonParser parser = new HighwaysJsonParser();
		URL url = new URL(urlStr);
		parser.parse(url);
		return parser.getParsedHighways();
	}

	public Network extract(String urlStr, TypesLibrary typesLibrary,
			NetworkFactory networkFactory) throws ModelInputException,
			JsonParseException, ProtocolException, IOException,
			TransformException {
		Highways highways = parse(urlStr);
		return extract(highways, typesLibrary, networkFactory);
	}

	public Network extract(Highways highways, TypesLibrary typesLibrary,
			NetworkFactory networkFactory) throws ModelInputException,
			JsonParseException, ProtocolException, IOException,
			TransformException {
		return createNetwork(highways, typesLibrary, networkFactory);
	}

	// TODO the osm nodes that has only two links if possible
	protected Network createNetwork(Highways highways,
			TypesLibrary typesLibrary, NetworkFactory networkFactory)
			throws ModelInputException, TransformException {
		Network network = networkFactory.createNetwork(seq, NETWORK_NAME);

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
							coords, roadInfo, networkFactory,
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

	private void create(Network network, Map<OsmNode, Node> nodes,
			OsmNode startOsmNode, OsmNode endOsmNode, OsmWay osmWay,
			List<Coordinate> coords, RoadInfo roadInfo,
			NetworkFactory networkFactory, NodeType nodeType, LinkType linkType)
			throws ModelInputException, TransformException {
		Node startNode = nodes.get(startOsmNode);
		if (startNode == null) {
			startNode = createNode(startOsmNode, nodeType, networkFactory);
			network.add(startNode);
			nodes.put(startOsmNode, startNode);
		}
		Node endNode = nodes.get(endOsmNode);
		if (endNode == null) {
			endNode = createNode(endOsmNode, nodeType, networkFactory);
			network.add(endNode);
			nodes.put(endOsmNode, endNode);
		}

		Link link = createLink(osmWay, linkType, startNode, endNode, coords,
				roadInfo, networkFactory);
		network.add(link);
		if (!osmWay.oneway) {
			Link reverseLink = createReverseLink(link, networkFactory);
			reverseLink.setRoadInfo(roadInfo);
			network.add(reverseLink);
		}

	}

	private Node createNode(OsmNode osmNode, NodeType nodeType,
			NetworkFactory networkFactory) {
		String name = String.valueOf(osmNode.id);
		for (OsmWay osmWay : osmNode.osmWays) {
			name += " @ " + osmWay.name;
		}
		Node node = networkFactory.createNode(seq, name, nodeType,
				osmNode.asCoord());
		// TODO edit node
		return node;
	}

	private Link createLink(OsmWay osmWay, LinkType linkType, Node startNode,
			Node endNode, List<Coordinate> coords, RoadInfo roadInfo,
			NetworkFactory networkFactory) throws ModelInputException,
			TransformException {
		Link link = networkFactory
				.createLink(seq, osmWay.name, linkType, startNode, endNode,
						coords.toArray(new Coordinate[0]), roadInfo);
		// TODO edit link
		return link;
	}

	private Link createReverseLink(Link link, NetworkFactory networkFactory)
			throws ModelInputException, TransformException {
		Link reverseLink = networkFactory.createReverseLink(seq,
				String.format("%s Reversed", link.getName()), link);
		reverseLink.setRoadInfo(link.getRoadInfo());
		return reverseLink;
	}

}
