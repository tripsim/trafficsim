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

import edu.trafficsim.api.model.Arc;
import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Location;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
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
			TransformCoordinateFilter filter) {
		location.getPoint().apply(filter);
		location.onGeomUpdated();
		location.onTransformDone(filter.getTargetCrs());
	}

	public static void transform(Arc arc, TransformCoordinateFilter filter) {
		arc.getLinearGeom().apply(filter);
		arc.onGeomUpdated();
		arc.onTransformDone(filter.getTargetCrs());
	}

	public static void transform(Network network,
			TransformCoordinateFilter filter) {
		for (Link link : network.getLinks())
			transform(link, filter);
		for (Node node : network.getNodes())
			transform(node, filter);
		network.onTransformDone(filter.getTargetCrs());
	}
}
