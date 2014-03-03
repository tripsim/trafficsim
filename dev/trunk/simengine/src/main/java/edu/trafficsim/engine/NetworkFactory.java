package edu.trafficsim.engine;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.ModelInputException;

public interface NetworkFactory {

	Network createNetwork(Long id, String name);

	Node createNode(Long id, String name, double x, double y);

	Node createNode(Long id, String name, Coordinate coord);

	LineString createLineString(Coordinate[] coords);

	LineString createLineString(CoordinateSequence points);

	Link createLink(Long id, String name, Node startNode, Node endNode,
			Coordinate[] coords) throws ModelInputException, TransformException;

	Link createLink(Long id, String name, Node startNode, Node endNode,
			CoordinateSequence points) throws ModelInputException,
			TransformException;

	Link createLink(Long id, String name, Node startNode, Node endNode,
			LineString linearGeom) throws ModelInputException,
			TransformException;

	Link createReverseLink(Long id, String name, Link link)
			throws ModelInputException, TransformException;

	Lane createLane(Long id, Link link, double start, double end, double width)
			throws ModelInputException, TransformException;

	Lane[] createLanes(Long[] ids, Link link, double start, double end,
			double width) throws ModelInputException, TransformException;

	RoadInfo createRoadInfo(Long id, String roadName, long osmId, String highway);

	ConnectionLane connect(Long id, Lane laneFrom, Lane laneTo, double width)
			throws ModelInputException, TransformException;

	Point createPoint(double x, double y);

	Point createPoint(Coordinate coord);

}
