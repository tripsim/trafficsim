package edu.trafficsim.engine;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.ModelInputException;

public interface NetworkFactory {

	public Network createEmptyNetwork(String name);

	public Node createNode(String name, Coordinate coord);

	public Link createLink(String name, Node startNode, Node endNode,
			Coordinate[] coords) throws ModelInputException, TransformException;

	public Link createReverseLink(String name, Link link)
			throws ModelInputException, TransformException;

	public Lane createLane(Link link, double start, double end, double width)
			throws ModelInputException, TransformException;

	public Lane[] createLanes(Link link, int num) throws ModelInputException,
			TransformException;

	public RoadInfo createRoadInfo(String roadName, long osmId, String highway);

	public ConnectionLane connect(Lane laneFrom, Lane laneTo)
			throws ModelInputException, TransformException;

}
