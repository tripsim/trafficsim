package edu.trafficsim.engine.factory;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.DefaultConnectionLane;
import edu.trafficsim.model.network.DefaultLane;
import edu.trafficsim.model.network.DefaultLink;
import edu.trafficsim.model.network.DefaultNetwork;
import edu.trafficsim.model.network.DefaultNode;

public class DefaultNetworkFactory extends AbstractFactory implements
		NetworkFactory {

	private static DefaultNetworkFactory factory;
	private GeometryFactory geometryFactory;

	private final static double DEFAULT_WIDTH = 4.0d;

	private DefaultNetworkFactory() {
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
	}

	public static DefaultNetworkFactory getInstance() {
		if (factory == null)
			factory = new DefaultNetworkFactory();
		return factory;
	}

	@Override
	public Network createEmptyNetwork(String name) {
		return new DefaultNetwork(newId, name);
	}

	// TODO set default types
	private static NodeType nodeType = new NodeType(0, "temp");
	private static LinkType linkType = new LinkType(0, "temp");

	public Point createPoint(double x, double y) {
		return geometryFactory.createPoint(new Coordinate(x, y));
	}

	public Point createPoint(Coordinate coord) {
		return geometryFactory.createPoint(coord);
	}

	public LineString createLineString(Coordinate[] coords) {
		return geometryFactory.createLineString(coords);
	}

	public DefaultNode createNode(String name, double x, double y) {
		return createNode(name, createPoint(x, y));
	}

	@Override
	public DefaultNode createNode(String name, Coordinate coord) {
		return createNode(name, createPoint(coord));
	}

	public DefaultNode createNode(String name, Point point) {
		return new DefaultNode(newId, name, nodeType, point);
	}

	@Override
	public DefaultLink createLink(String name, Node startNode, Node endNode,
			Coordinate[] coords) throws ModelInputException, TransformException {
		LineString lineString = createLineString(coords);
		return createLink(name, startNode, endNode, lineString);
	}

	public DefaultLink createLink(String name, Node startNode, Node endNode,
			LineString lineString) throws ModelInputException,
			TransformException {
		DefaultLink link = new DefaultLink(newId, name, linkType, startNode,
				endNode, lineString);
		startNode.add(link);
		endNode.add(link);
		return link;
	}

	@Override
	public DefaultLink createReverseLink(String name, Link link)
			throws ModelInputException, TransformException {
		DefaultLink newLink = createLink(name, link.getEndNode(),
				link.getStartNode(), (LineString) link.getLinearGeom()
						.reverse());
		link.setReverseLink(newLink);
		return newLink;
	}

	@Override
	public DefaultLane createLane(Link link, double start, double end,
			double width) throws ModelInputException, TransformException {
		return new DefaultLane(newId, link, start, end, width);
	}

	@Override
	public Lane[] createLanes(Link link, int num) throws ModelInputException,
			TransformException {
		for (int i = 0; i < num; i++) {
			createLane(link, 0, 1, DEFAULT_WIDTH);
		}
		return link.getLanes();
	}

	@Override
	public RoadInfo createRoadInfo(String roadName, long osmId, String highway) {
		return new RoadInfo(roadName, osmId, highway);
	}

	@Override
	public ConnectionLane connect(Lane laneFrom, Lane laneTo)
			throws ModelInputException, TransformException {
		DefaultConnectionLane connectionLane = new DefaultConnectionLane(
				newId, laneFrom, laneTo, DEFAULT_WIDTH);
		return connectionLane;
	}
}
