/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.util;

import org.tripsim.api.model.Arc;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Location;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.model.util.GeoReferencing.TransformCoordinateFilter;

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
