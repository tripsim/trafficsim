package edu.trafficsim.engine.factory;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
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
import edu.trafficsim.model.network.DefaultRoadInfo;
import edu.trafficsim.utility.Sequence;

public class DefaultNetworkFactory extends AbstractFactory implements
		NetworkFactory {

	private static DefaultNetworkFactory factory;
	private GeometryFactory geometryFactory;

	private DefaultNetworkFactory() {
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
	}

	public static NetworkFactory getInstance() {
		if (factory == null)
			factory = new DefaultNetworkFactory();
		return factory;
	}

	@Override
	public Network createNetwork(Sequence seq, String name) {
		return new DefaultNetwork(seq.nextId(), name);
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

	@Override
	public LineString createLineString(CoordinateSequence points) {
		return geometryFactory.createLineString(points);
	}

	@Override
	public DefaultNode createNode(Sequence seq, String name, double x, double y) {
		return createNode(seq, name, createPoint(x, y));
	}

	@Override
	public DefaultNode createNode(Sequence seq, String name, Coordinate coord) {
		return createNode(seq, name, createPoint(coord));
	}

	public DefaultNode createNode(Sequence seq, String name, Point point) {
		return new DefaultNode(seq.nextId(), name, nodeType, point);
	}

	@Override
	public DefaultLink createLink(Sequence seq, String name, Node startNode,
			Node endNode, Coordinate[] coords, RoadInfo roadInfo)
			throws ModelInputException, TransformException {
		return createLink(seq, name, startNode, endNode,
				createLineString(coords), roadInfo);
	}

	@Override
	public Link createLink(Sequence seq, String name, Node startNode,
			Node endNode, CoordinateSequence points, RoadInfo roadInfo)
			throws ModelInputException, TransformException {
		return createLink(seq, name, startNode, endNode,
				createLineString(points), roadInfo);
	}

	@Override
	public DefaultLink createLink(Sequence seq, String name, Node startNode,
			Node endNode, LineString lineString, RoadInfo roadInfo)
			throws ModelInputException, TransformException {
		if (roadInfo == null)
			roadInfo = createRoadInfo(seq, name);
		return new DefaultLink(seq.nextId(), name, linkType, startNode,
				endNode, lineString, roadInfo);
	}

	@Override
	public DefaultLink createReverseLink(Sequence seq, String name, Link link)
			throws ModelInputException, TransformException {
		DefaultLink newLink = createLink(seq, name, link.getEndNode(),
				link.getStartNode(), (LineString) link.getLinearGeom()
						.reverse(), link.getRoadInfo());
		link.setReverseLink(newLink);
		return newLink;
	}

	@Override
	public DefaultLane createLane(Sequence seq, Link link, double start,
			double end, double width) throws ModelInputException,
			TransformException {
		return new DefaultLane(seq.nextId(), link, start, end, width);
	}

	@Override
	public ConnectionLane connect(Sequence seq, Lane laneFrom, Lane laneTo,
			double width) throws ModelInputException, TransformException {
		DefaultConnectionLane connectionLane = new DefaultConnectionLane(
				seq.nextId(), laneFrom, laneTo, width);
		return connectionLane;
	}

	@Override
	public Lane[] createLanes(Sequence seq, Link link, double start,
			double end, double width, int numOfLanes)
			throws ModelInputException, TransformException {
		for (int i = 0; i < numOfLanes; i++) {
			createLane(seq, link, start, end, width);
		}
		return link.getLanes();
	}

	@Override
	public RoadInfo createRoadInfo(Sequence seq, String name, long osmId,
			String highway) {
		return new DefaultRoadInfo(seq.nextId(), name, osmId, highway);
	}

	@Override
	public RoadInfo createRoadInfo(Sequence seq, String name) {
		return new DefaultRoadInfo(seq.nextId(), name);
	}

}
