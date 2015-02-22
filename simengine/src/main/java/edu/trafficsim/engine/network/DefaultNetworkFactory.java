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
package edu.trafficsim.engine.network;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
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
@Component("default-network-factory")
class DefaultNetworkFactory implements NetworkFactory {

	private GeometryFactory geometryFactory;

	DefaultNetworkFactory() {
		geometryFactory = JTSFactoryFinder.getGeometryFactory();
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
	public Network createNetwork(Long id, String name) {
		return new DefaultNetwork(id, name);
	}

	@Override
	public Node createNode(Long id, String name, String nodeType, double x,
			double y) {
		return createNode(id, name, nodeType, createPoint(x, y));
	}

	@Override
	public Node createNode(Long id, String name, String nodeType,
			Coordinate coord) {
		return createNode(id, name, nodeType, createPoint(coord));
	}

	@Override
	public Node createNode(Long id, String name, String nodeType, Point point) {
		return new DefaultNode(id, name, nodeType, point);
	}

	@Override
	public Link createLink(Long id, String name, String linkType,
			Node startNode, Node endNode, Coordinate[] coords, RoadInfo roadInfo)
			throws ModelInputException {
		return createLink(id, name, linkType, startNode, endNode,
				createLineString(coords), roadInfo);
	}

	@Override
	public Link createLink(Long id, String name, String linkType,
			Node startNode, Node endNode, CoordinateSequence points,
			RoadInfo roadInfo) throws ModelInputException {
		return createLink(id, name, linkType, startNode, endNode,
				createLineString(points), roadInfo);
	}

	@Override
	public Link createLink(Long id, String name, String linkType,
			Node startNode, Node endNode, LineString lineString,
			RoadInfo roadInfo) throws ModelInputException {
		if (roadInfo == null) {
			roadInfo = new DefaultRoadInfo();
		}
		return new DefaultLink(id, name, linkType, startNode, endNode,
				lineString, roadInfo);
	}

	@Override
	public Link createReverseLink(Long id, String name, Link link)
			throws ModelInputException {
		Link newLink = createLink(id, name, link.getLinkType(),
				link.getEndNode(), link.getStartNode(), (LineString) link
						.getLinearGeom().reverse(), link.getRoadInfo());
		link.setReverseLink(newLink);
		return newLink;
	}

	@Override
	public Lane createLane(Long id, Link link, double start, double end,
			double width) throws ModelInputException {
		return new DefaultLane(id, link, start, end, width);
	}

	@Override
	public Lane[] createLanes(Long[] ids, Link link, double start, double end,
			double width) throws ModelInputException {
		for (Long id : ids) {
			createLane(id, link, start, end, width);
		}
		return link.getLanes();
	}

	@Override
	public ConnectionLane connect(Long id, Lane laneFrom, Lane laneTo,
			double width) throws ModelInputException {
		return new DefaultConnectionLane(id, laneFrom, laneTo, width);
	}

	@Override
	public RoadInfo createRoadInfo(Long id, String roadName) {
		return new DefaultRoadInfo(id, roadName);
	}

	@Override
	public RoadInfo createRoadInfo(Long id, String name, long osmId,
			String highway) {
		return new DefaultRoadInfo(id, name, osmId, highway);
	}

}
