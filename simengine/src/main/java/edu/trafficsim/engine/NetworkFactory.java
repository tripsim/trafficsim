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
package edu.trafficsim.engine;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.ModelInputException;

/**
 * A factory for creating Network objects.
 */
public interface NetworkFactory {

	Point createPoint(double x, double y);

	Point createPoint(Coordinate coord);

	LineString createLineString(Coordinate[] coords);

	LineString createLineString(CoordinateSequence points);

	Network createNetwork(Sequence seq, String name);

	Network createNetwork(Long id, String name);

	Node createNode(Sequence seq, String name, NodeType type, double x, double y);

	Node createNode(Sequence seq, String name, NodeType type, Coordinate coord);

	Node createNode(Sequence seq, String name, NodeType type, Point point);

	Node createNode(Long id, String name, NodeType type, Point point);

	Link createLink(Sequence seq, String name, LinkType type, Node startNode,
			Node endNode, Coordinate[] coords, RoadInfo roadInfo)
			throws ModelInputException, TransformException;

	Link createLink(Sequence seq, String name, LinkType type, Node startNode,
			Node endNode, CoordinateSequence points, RoadInfo roadInfo)
			throws ModelInputException, TransformException;

	Link createLink(Sequence seq, String name, LinkType type, Node startNode,
			Node endNode, LineString linearGeom, RoadInfo roadInfo)
			throws ModelInputException, TransformException;

	Link createLink(Long id, String name, LinkType type, Node startNode,
			Node endNode, LineString linearGeom, RoadInfo roadInfo)
			throws ModelInputException, TransformException;

	Link createReverseLink(Sequence seq, String name, Link link)
			throws ModelInputException, TransformException;

	Lane createLane(Sequence seq, Link link, double start, double end,
			double width) throws ModelInputException, TransformException;

	Lane createLane(Long id, Link link, double start, double end, double width)
			throws ModelInputException, TransformException;

	Lane[] createLanes(Sequence seq, Link link, double start, double end,
			double width, int numOfLanes) throws ModelInputException,
			TransformException;

	ConnectionLane connect(Sequence seq, Lane laneFrom, Lane laneTo,
			double width) throws ModelInputException, TransformException;

	ConnectionLane connect(Long id, Lane laneFrom, Lane laneTo, double width)
			throws ModelInputException, TransformException;

	RoadInfo createRoadInfo(Sequence seq, String roadName, long roadId,
			String highway);

	RoadInfo createRoadInfo(Long id, String roadName, long roadId,
			String highway);

	RoadInfo createRoadInfo(Sequence seq, String roadName);

}
