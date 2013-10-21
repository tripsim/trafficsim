package edu.trafficsim.engine.factory;

import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.DefaultConnector;
import edu.trafficsim.model.network.DefaultLane;
import edu.trafficsim.model.network.DefaultLink;
import edu.trafficsim.model.network.DefaultNode;
import edu.trafficsim.model.network.LinkType;
import edu.trafficsim.model.network.NodeType;

public class DefaultNetworkFactory extends AbstractFactory implements
		NetworkFactory {

	private static DefaultNetworkFactory factory;
	private GeometryFactory geometryFactory;

	private final static double DEFAULT_RADIUS = 20.0d;
	private final static double DEFAULT_WIDTH = 4.0d;

	private DefaultNetworkFactory() {
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
	}

	private static NodeType nodeType = new NodeType();
	private static LinkType linkType = new LinkType();

	public static DefaultNetworkFactory getInstance() {
		if (factory == null)
			factory = new DefaultNetworkFactory();
		return factory;
	}

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
		return new DefaultNode(name, nodeType, point, DEFAULT_RADIUS);
	}

	public DefaultNode createNode(String name, Point point, double radius,
			Router router) {
		return new DefaultNode(name, nodeType, point, DEFAULT_RADIUS, router);
	}

	@Override
	public DefaultLink createLink(String name, Node startNode, Node endNode,
			Coordinate[] coords) {
		LineString lineString = createLineString(coords);
		DefaultLink link = new DefaultLink(name, linkType, startNode, endNode,
				lineString);
		try {
			startNode.add(link);
		} catch (ModelInputException e) {
			e.printStackTrace();
			return null;
		}
		return link;
	}

	@Override
	public DefaultLane createLane(Link link, double start, double end,
			double width, double shift, int laneId) {
		try {
			return new DefaultLane(link, start, end, width, shift, laneId);
		} catch (ModelInputException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Lane> createLanes(Link link, int num) {
		for (int i = 0; i < num; i++) {
			double shift = (num / 2.0 - i) * DEFAULT_WIDTH;
			DefaultLane lane = new DefaultLane(link, DEFAULT_WIDTH, shift, i);
			link.add(lane);
		}
		return link.getLanes();
	}

	@Override
	public DefaultConnector createConnector(Lane laneFrom, Lane laneTo) {
		DefaultConnector connector;
		try {
			connector = new DefaultConnector(laneFrom, laneTo, DEFAULT_WIDTH);
		} catch (ModelInputException e) {
			e.printStackTrace();
			// TODO error message log
			return null;
		}
		Node node = ((Link) laneFrom.getSegment()).getEndNode();
		try {
			node.add(connector);
		} catch (ModelInputException e) {
			e.printStackTrace();
			// TODO error message log
			return null;
		}
		return connector;
	}
}
