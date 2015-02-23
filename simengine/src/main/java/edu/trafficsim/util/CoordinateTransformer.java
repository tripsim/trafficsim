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

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Location;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.util.GeoReferencing.TransformCoordinateFilter;

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
	 * @throws ModelInputException
	 */
	public static void transform(Location location,
			TransformCoordinateFilter filter) throws ModelInputException {
		location.getPoint().apply(filter);
		location.onGeomUpdated();
		location.onTransformDone(filter.getTargetCrs());
	}

	public static void transform(Segment segment,
			TransformCoordinateFilter filter) throws ModelInputException {
		segment.getLinearGeom().apply(filter);
		segment.onGeomUpdated();
		segment.onTransformDone(filter.getTargetCrs());
	}

	public static void transform(Network network,
			TransformCoordinateFilter filter) throws ModelInputException {
		for (Link link : network.getLinks())
			transform(link, filter);
		for (Node node : network.getNodes())
			transform(node, filter);
		network.onTransformDone(filter.getTargetCrs());
	}
}
