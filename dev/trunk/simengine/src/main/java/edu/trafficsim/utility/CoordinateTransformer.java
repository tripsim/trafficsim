package edu.trafficsim.utility;

import com.vividsolutions.jts.geom.CoordinateFilter;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Location;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Segment;

public class CoordinateTransformer {

	/**
	 * @param filter
	 *            which is used to transform all the coordinates of the
	 *            components in the network
	 */
	public static void transform(Location location, CoordinateFilter filter) {
		location.getPoint().apply(filter);
	}

	public static void transform(Segment segment, CoordinateFilter filter) {
		segment.getLinearGeom().apply(filter);
	}

	public static void transform(Network network, CoordinateFilter filter) {
		for (Link link : network.getLinks())
			transform(link, filter);
		for (Node node : network.getNodes())
			transform(node, filter);
	}
}
