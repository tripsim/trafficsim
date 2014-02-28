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

	private DefaultNetworkFactory() {
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
	}

	public static DefaultNetworkFactory getInstance() {
		if (factory == null)
			factory = new DefaultNetworkFactory();
		return factory;
	}

	@Override
	public Network createEmptyNetwork(Long id, String name) {
		return new DefaultNetwork(id, name);
	}

	// TODO set default types
	private static NodeType nodeType = new NodeType(0, "temp");
	private static LinkType linkType = new LinkType(0, "temp");

	@Override
	public Point createPoint(double x, double y) {
		return geometryFactory.createPoint(new Coordinate(x, y));
	}

	@Override
	public Point createPoint(Coordinate coord) {
		return geometryFactory.createPoint(coord);
	}

	@Override
	public LineString createLineString(Coordinate[] coords) {
		return geometryFactory.createLineString(coords);
	}

	public DefaultNode createNode(Long id, String name, double x, double y) {
		return createNode(id, name, createPoint(x, y));
	}

	@Override
	public DefaultNode createNode(Long id, String name, Coordinate coord) {
		return createNode(id, name, createPoint(coord));
	}

	public DefaultNode createNode(Long id, String name, Point point) {
		return new DefaultNode(id, name, nodeType, point);
	}

	@Override
	public DefaultLink createLink(Long id, String name, Node startNode,
			Node endNode, Coordinate[] coords) throws ModelInputException,
			TransformException {
		LineString lineString = createLineString(coords);
		return createLink(id, name, startNode, endNode, lineString);
	}

	public DefaultLink createLink(Long id, String name, Node startNode,
			Node endNode, LineString lineString) throws ModelInputException,
			TransformException {
		DefaultLink link = new DefaultLink(id, name, linkType, startNode,
				endNode, lineString);
		startNode.add(link);
		endNode.add(link);
		return link;
	}

	@Override
	public DefaultLink createReverseLink(Long id, String name, Link link)
			throws ModelInputException, TransformException {
		DefaultLink newLink = createLink(id, name, link.getEndNode(),
				link.getStartNode(), (LineString) link.getLinearGeom()
						.reverse());
		link.setReverseLink(newLink);
		return newLink;
	}

	@Override
	public DefaultLane createLane(Long id, Link link, double start, double end,
			double width) throws ModelInputException, TransformException {
		return new DefaultLane(id, link, start, end, width);
	}

	@Override
	public Lane[] createLanes(Long[] ids, Link link, double start, double end,
			double width)
			throws ModelInputException, TransformException {
		for (Long id : ids) {
			createLane(id, link, start, end, width);
		}
		return link.getLanes();
	}

	@Override
	public RoadInfo createRoadInfo(Long id, String roadName, long osmId,
			String highway) {
		return new RoadInfo(roadName, osmId, highway);
	}

	@Override
	public ConnectionLane connect(Long id, Lane laneFrom, Lane laneTo, double width)
			throws ModelInputException, TransformException {
		DefaultConnectionLane connectionLane = new DefaultConnectionLane(id,
				laneFrom, laneTo, width);
		return connectionLane;
	}
}
