package edu.trafficsim.engine.osm;

import java.io.IOException;
import java.io.Reader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.osm.Highways.OsmNode;
import edu.trafficsim.engine.osm.Highways.OsmWay;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.Coordinates;
import edu.trafficsim.model.core.Coordinates.TransformCoordinateFilter;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.CoordinateTransformer;

public class OsmNetworkExtractor {

	private static final String NETWORK_NAME = "New Network";
	private static final TransformCoordinateFilter filter = Coordinates
			.getDefaultTransformFilter();

	private static OsmNetworkExtractor extractor = null;

	private OsmNetworkExtractor() {
	}

	public static OsmNetworkExtractor getInstance() {
		if (extractor == null)
			extractor = new OsmNetworkExtractor();
		return extractor;
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

	public Network extract(String urlStr, NetworkFactory networkFactory)
			throws ModelInputException, JsonParseException, ProtocolException,
			IOException {
		Highways highways = parse(urlStr);
		return extract(highways, networkFactory);
	}

	public Network extract(Highways highways, NetworkFactory networkFactory)
			throws ModelInputException, JsonParseException, ProtocolException,
			IOException {
		return createNetwork(highways, networkFactory);
	}

	protected Network createNetwork(Highways highways,
			NetworkFactory networkFactory) throws ModelInputException {
		Network network = networkFactory.createEmptyNetwork(NETWORK_NAME);

		for (OsmWay osmWay : highways.getOsmWays()) {
			List<Coordinate> coords = new ArrayList<Coordinate>();

			int i = 0;
			// read the first osmnode, and add to coords
			OsmNode startOsmNode = osmWay.osmNodes.get(i);
			coords.add(startOsmNode.asCoord());
			OsmNode endOsmNode = null;
			while (++i < osmWay.osmNodes.size()) {
				// read the next osmnode and add to coords until end
				endOsmNode = osmWay.osmNodes.get(i);
				coords.add(endOsmNode.asCoord());
				// if the osmnode is shared or at end, create a link based on
				// that coords, and clear the coords, make the endOsmNode as
				// start
				// node for next link
				if (endOsmNode.isShared() || i == osmWay.osmNodes.size() - 1) {
					create(network, startOsmNode, endOsmNode, osmWay, coords,
							networkFactory);
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

	private void create(Network network, OsmNode startOsmNode,
			OsmNode endOsmNode, OsmWay osmWay, List<Coordinate> coords,
			NetworkFactory networkFactory) throws ModelInputException {
		Node startNode = network.getNode(startOsmNode.id);
		if (startNode == null) {
			startNode = createNode(startOsmNode, networkFactory);
			network.add(startNode);
		}
		Node endNode = network.getNode(endOsmNode.id);
		if (endNode == null) {
			endNode = createNode(endOsmNode, networkFactory);
			network.add(endNode);
		}
		RoadInfo roadInfo = networkFactory.createRoadInfo(osmWay.name,
				osmWay.id, osmWay.highway);
		Link link = createLink(osmWay, startNode, endNode, coords,
				networkFactory);
		link.setRoadInfo(roadInfo);
		network.add(link);
		if (!osmWay.oneway) {
			Link reverseLink = createReverseLink(link, networkFactory);
			reverseLink.setRoadInfo(roadInfo);
			network.add(reverseLink);
		}

	}

	private Node createNode(OsmNode osmNode, NetworkFactory networkFactory) {
		Node node = networkFactory.createNode(String.valueOf(osmNode.id),
				osmNode.asCoord());
		// TODO edit node
		return node;
	}

	private Link createLink(OsmWay osmWay, Node startNode, Node endNode,
			List<Coordinate> coords, NetworkFactory networkFactory)
			throws ModelInputException {
		Link link = networkFactory.createLink(osmWay.name, startNode, endNode,
				coords.toArray(new Coordinate[0]));
		// TODO edit link
		return link;
	}

	private Link createReverseLink(Link link, NetworkFactory networkFactory)
			throws ModelInputException {
		Link reverseLink = networkFactory.createReverseLink(
				String.format("%s Reversed", link.getName()), link);
		return reverseLink;
	}
}
