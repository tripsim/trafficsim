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
package org.tripsim.engine.network;

import java.util.List;

import org.tripsim.api.model.Connector;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.RoadInfo;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

/**
 * A factory for creating Network objects.
 */
public interface NetworkFactory {

	Point createPoint(double x, double y);

	Point createPoint(Coordinate coord);

	LineString createLineString(Coordinate[] coords);

	LineString createLineString(CoordinateSequence points);

	Network createNetwork(String name);

	Node createNode(Long id, String type, double x, double y);

	Node createNode(Long id, String type, Coordinate coord);

	Node createNode(Long id, String type, Point point);

	Link createLink(Long id, String type, Node startNode, Node endNode,
			Coordinate[] coords, RoadInfo roadInfo);

	Link createLink(Long id, String type, Node startNode, Node endNode,
			CoordinateSequence points, RoadInfo roadInfo);

	Link createLink(Long id, String type, Node startNode, Node endNode,
			LineString linearGeom, RoadInfo roadInfo);

	Link createReverseLink(Long id, Link link);

	Lane createLane(Long id, Link link, double start, double end, double width);

	List<Lane> createLanes(Long[] ids, Link link, double start, double end,
			double width);

	Connector connect(Long id, Lane laneFrom, Lane laneTo);

	RoadInfo createRoadInfo(Long id, long roadId, String roadName,
			String highway);

	RoadInfo createRoadInfo(Long id, String roadName);

}
