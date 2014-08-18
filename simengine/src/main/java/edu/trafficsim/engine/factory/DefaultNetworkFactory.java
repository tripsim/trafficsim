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

/**
 * A factory for creating DefaultNetwork objects.
 * 
 * @author Xuan Shi
 */
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
		return createNetwork(seq.nextId(), name);
	}

	@Override
	public Network createNetwork(Long id, String name) {
		return new DefaultNetwork(id, name);
	}

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
	public Node createNode(Sequence seq, String name, NodeType type, double x,
			double y) {
		return createNode(seq, name, type, createPoint(x, y));
	}

	@Override
	public Node createNode(Sequence seq, String name, NodeType type,
			Coordinate coord) {
		return createNode(seq, name, type, createPoint(coord));
	}

	@Override
	public Node createNode(Sequence seq, String name, NodeType type, Point point) {
		return createNode(seq.nextId(), name, type, point);
	}

	@Override
	public Node createNode(Long id, String name, NodeType type, Point point) {
		return new DefaultNode(id, name, type, point);
	}

	@Override
	public Link createLink(Sequence seq, String name, LinkType type,
			Node startNode, Node endNode, Coordinate[] coords, RoadInfo roadInfo)
			throws ModelInputException, TransformException {
		return createLink(seq, name, type, startNode, endNode,
				createLineString(coords), roadInfo);
	}

	@Override
	public Link createLink(Sequence seq, String name, LinkType type,
			Node startNode, Node endNode, CoordinateSequence points,
			RoadInfo roadInfo) throws ModelInputException, TransformException {
		return createLink(seq, name, type, startNode, endNode,
				createLineString(points), roadInfo);
	}

	@Override
	public Link createLink(Sequence seq, String name, LinkType type,
			Node startNode, Node endNode, LineString lineString,
			RoadInfo roadInfo) throws ModelInputException, TransformException {
		if (roadInfo == null)
			roadInfo = createRoadInfo(seq, name);
		return createLink(seq.nextId(), name, type, startNode, endNode,
				lineString, roadInfo);
	}

	@Override
	public Link createLink(Long id, String name, LinkType type, Node startNode,
			Node endNode, LineString lineString, RoadInfo roadInfo)
			throws ModelInputException, TransformException {
		if (roadInfo == null)
			throw new ModelInputException("Road info cannot be null.");
		return new DefaultLink(id, name, type, startNode, endNode, lineString,
				roadInfo);
	}

	@Override
	public Link createReverseLink(Sequence seq, String name, Link link)
			throws ModelInputException, TransformException {
		Link newLink = createLink(seq, name, link.getLinkType(),
				link.getEndNode(), link.getStartNode(), (LineString) link
						.getLinearGeom().reverse(), link.getRoadInfo());
		link.setReverseLink(newLink);
		return newLink;
	}

	@Override
	public Lane createLane(Sequence seq, Link link, double start, double end,
			double width) throws ModelInputException, TransformException {
		return createLane(seq.nextId(), link, start, end, width);
	}

	@Override
	public Lane createLane(Long id, Link link, double start, double end,
			double width) throws ModelInputException, TransformException {
		return new DefaultLane(id, link, start, end, width);
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
	public ConnectionLane connect(Sequence seq, Lane laneFrom, Lane laneTo,
			double width) throws ModelInputException, TransformException {
		return connect(seq.nextId(), laneFrom, laneTo, width);

	}

	@Override
	public ConnectionLane connect(Long id, Lane laneFrom, Lane laneTo,
			double width) throws ModelInputException, TransformException {
		return new DefaultConnectionLane(id, laneFrom, laneTo, width);
	}

	@Override
	public RoadInfo createRoadInfo(Sequence seq, String name, long osmId,
			String highway) {
		return createRoadInfo(seq.nextId(), name, osmId, highway);
	}

	@Override
	public RoadInfo createRoadInfo(Long id, String name, long osmId,
			String highway) {
		return new DefaultRoadInfo(id, name, osmId, highway);
	}

	@Override
	public RoadInfo createRoadInfo(Sequence seq, String name) {
		return new DefaultRoadInfo(seq.nextId(), name);
	}

}
