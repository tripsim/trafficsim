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

	public Network createEmptyNetwork(Long id, String name);

	public Node createNode(Long id, String name, Coordinate coord);

	public Link createLink(Long id, String name, Node startNode, Node endNode,
			Coordinate[] coords) throws ModelInputException, TransformException;

	public Link createReverseLink(Long id, String name, Link link)
			throws ModelInputException, TransformException;

	public Lane createLane(Long id, Link link, double start, double end,
			double width) throws ModelInputException, TransformException;

	public Lane[] createLanes(Long[] ids, Link link)
			throws ModelInputException, TransformException;

	public RoadInfo createRoadInfo(Long id, String roadName, long osmId,
			String highway);

	public ConnectionLane connect(Long id, Lane laneFrom, Lane laneTo)
			throws ModelInputException, TransformException;
}
