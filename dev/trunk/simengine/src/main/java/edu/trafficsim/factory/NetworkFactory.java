package edu.trafficsim.factory;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.network.LinkType;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.network.NodeType;

public class NetworkFactory extends AbstractFactory {
	
	private static NetworkFactory factory;
	private GeometryFactory geometryFactory;
	
	private NetworkFactory() {
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
	} 
	
	private static NodeType nodeType = new NodeType();
	private static LinkType linkType = new LinkType();

	public static NetworkFactory getInstance() {
		if(factory == null)
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
		return new Node(name, nodeType, point);
	}
	
	public Link createLink(String name, Node nodeFrom, Node nodeTo, Coordinate[] coords) {
		LineString lineString = createLineString(coords);
		return new Link(name, linkType, nodeFrom, nodeTo, lineString);
	}
	
	public Lane createLane(Link link) {
		return createLane(link, Lane.Direction.Forward);
	}
	
	public Lane createLane(Link link, Lane.Direction direction) {
		return new Lane(link, direction);
	}
}
