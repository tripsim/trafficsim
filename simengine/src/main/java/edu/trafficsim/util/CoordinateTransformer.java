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
package edu.trafficsim.util;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.CoordinateFilter;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Location;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Segment;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class CoordinateTransformer {

	/**
	 * @param filter
	 *            which is used to transform all the coordinates of the
	 *            components in the network
	 * @throws TransformException
	 */
	public static void transform(Location location, CoordinateFilter filter)
			throws TransformException {
		location.getPoint().apply(filter);
		location.onGeomUpdated();
	}

	public static void transform(Segment segment, CoordinateFilter filter)
			throws TransformException {
		segment.getLinearGeom().apply(filter);
		segment.onGeomUpdated();
	}

	public static void transform(Network network, CoordinateFilter filter)
			throws TransformException {
		for (Link link : network.getLinks())
			transform(link, filter);
		for (Node node : network.getNodes())
			transform(node, filter);
		network.discover();
	}
}