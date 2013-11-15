package edu.trafficsim.utility;

import org.opengis.referencing.operation.TransformException;

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
	}
}
