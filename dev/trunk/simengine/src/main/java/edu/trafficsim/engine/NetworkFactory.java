package edu.trafficsim.engine;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Connector;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.core.ModelInputException;

public interface NetworkFactory {

	public Network createEmptyNetwork(String name);

	public Node createNode(String name, Coordinate coord);

	public Link createLink(String name, Node startNode, Node endNode,
			Coordinate[] coords) throws ModelInputException;

	public Link createReverseLink(String name, Link link)
			throws ModelInputException;

	public Lane createLane(Segment segment, double start, double end,
			double width, double shift, int laneId) throws ModelInputException;

	public List<Lane> createLanes(Link link, int num)
			throws ModelInputException;

	public Connector createConnector(Lane laneFrom, Lane laneTo)
			throws ModelInputException;

	public RoadInfo createRoadInfo(String roadName, long osmId, String highway);

	// public void connect(Lane laneFrom, Lane laneTo)
	// throws ModelInputException;
	//
	// public void disconnect(Lane laneFrom, Lane laneTo)
	// throws ModelInputException;
}
