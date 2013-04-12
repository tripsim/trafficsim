package edu.trafficsim.factory;

import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.Connector;
import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.network.LinkType;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.network.NodeType;

public class NetworkFactory extends AbstractFactory {

	private static NetworkFactory factory;
	private GeometryFactory geometryFactory;

	private final static double DEFAULT_RADIUS = 4.0d;
	private final static double DEFAULT_WIDTH = 4.0d;

	private NetworkFactory() {
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
	}

	private static NodeType nodeType = new NodeType();
	private static LinkType linkType = new LinkType();

	public static NetworkFactory getInstance() {
		if (factory == null)
			factory = new NetworkFactory();
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

	public Node createNode(String name, double x, double y) {
		return createNode(name, createPoint(x, y));
	}

	public Node createNode(String name, Coordinate coord) {
		return createNode(name, createPoint(coord));
	}

	public Node createNode(String name, Point point) {
		return new Node(name, nodeType, point, DEFAULT_RADIUS);
	}

	public Link createLink(String name, Node startNode, Node endNode,
			Coordinate[] coords) {
		LineString lineString = createLineString(coords);
		return new Link(name, linkType, startNode, endNode, lineString);
	}

	public List<Lane> createLanes(Link link, int num)
			throws ModelInputException {
		link.removeAllLanes();
		for (int i = 0; i < num; i++) {
			double shift = (num / 2.0 - i) * DEFAULT_WIDTH;
			Lane lane = new Lane(i, link, DEFAULT_WIDTH, shift);
			link.addLane(lane);
		}
		return link.getLanes();
	}

	public Connector createConnector(Lane laneFrom, Lane laneTo)
			throws ModelInputException {
		LineString linearGeom = createLineString(new Coordinate[] {
				laneFrom.getEndCoord(), laneTo.getStartCoord() });
		return new Connector(laneFrom, laneTo, linearGeom);
	}
}
