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
import edu.trafficsim.utility.Sequence;

public interface NetworkFactory {

	Point createPoint(double x, double y);

	Point createPoint(Coordinate coord);

	LineString createLineString(Coordinate[] coords);

	LineString createLineString(CoordinateSequence points);

	Network createNetwork(Sequence seq, String name);

	Node createNode(Sequence seq, String name, double x, double y);

	Node createNode(Sequence seq, String name, Coordinate coord);

	Link createLink(Sequence seq, String name, Node startNode, Node endNode,
			Coordinate[] coords, RoadInfo roadInfo) throws ModelInputException,
			TransformException;

	Link createLink(Sequence seq, String name, Node startNode, Node endNode,
			CoordinateSequence points, RoadInfo roadInfo)
			throws ModelInputException, TransformException;

	Link createLink(Sequence seq, String name, Node startNode, Node endNode,
			LineString linearGeom, RoadInfo roadInfo)
			throws ModelInputException, TransformException;

	Link createReverseLink(Sequence seq, String name, Link link)
			throws ModelInputException, TransformException;

	Lane createLane(Sequence seq, Link link, double start, double end,
			double width) throws ModelInputException, TransformException;

	Lane[] createLanes(Sequence seq, Link link, double start, double end,
			double width, int numOfLanes) throws ModelInputException,
			TransformException;

	ConnectionLane connect(Sequence seq, Lane laneFrom, Lane laneTo,
			double width) throws ModelInputException, TransformException;

	RoadInfo createRoadInfo(Sequence seq, String roadName, long roadId,
			String highway);

	RoadInfo createRoadInfo(Sequence seq, String roadName);

}
