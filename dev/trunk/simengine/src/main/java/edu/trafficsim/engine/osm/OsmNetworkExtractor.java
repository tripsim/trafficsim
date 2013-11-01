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
import edu.trafficsim.model.core.Coordinates;
import edu.trafficsim.model.core.Coordinates.TransformCoordinateFilter;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.CoordinateTransformer;

public class OsmNetworkExtractor {

	private static final String NETWORK_NAME = "New Network";
	private static final TransformCoordinateFilter filter = Coordinates
			.getDefaultTransformFilter();

	private NetworkFactory networkFactory;

	public OsmNetworkExtractor(NetworkFactory networkFactory) {
		this.networkFactory = networkFactory;
	}

	public Network extract(Reader reader) throws ModelInputException,
			JsonParseException, IOException {
		HighwaysJsonParser parser = new HighwaysJsonParser();
		parser.parse(reader);
		return getNetwork(parser.getParsedHighways());
	}

	public Network extract(String urlStr) throws ModelInputException,
			JsonParseException, ProtocolException, IOException {
		HighwaysJsonParser parser = new HighwaysJsonParser();
		URL url = new URL(urlStr);
		parser.parse(url);
		return getNetwork(parser.getParsedHighways());
	}

	protected Network getNetwork(Highways highways) throws ModelInputException {
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
					create(network, startOsmNode, endOsmNode, osmWay, coords);
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
			OsmNode endOsmNode, OsmWay osmWay, List<Coordinate> coords)
			throws ModelInputException {
		Node startNode = network.getNode(startOsmNode.id);
		if (startNode == null) {
			startNode = createNode(startOsmNode);
			network.add(startNode);
		}
		Node endNode = network.getNode(endOsmNode.id);
		if (endNode == null) {
			endNode = createNode(endOsmNode);
			network.add(endNode);
		}
		Link link = createLink(osmWay, startNode, endNode, coords);
		network.add(link);
	}

	private Node createNode(OsmNode osmNode) {
		Node node = networkFactory.createNode(String.valueOf(osmNode.id),
				osmNode.asCoord());
		// TODO add additional info like highways
		return node;
	}

	private Link createLink(OsmWay osmWay, Node startNode, Node endNode,
			List<Coordinate> coords) throws ModelInputException {
		Link link = networkFactory.createLink(osmWay.name, startNode, endNode,
				coords.toArray(new Coordinate[0]));
		// TODO add additional info like highways
		return link;
	}
}
