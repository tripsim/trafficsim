package edu.trafficsim.engine;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Connector;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;

public interface NetworkFactory {

	public Node createNode(String name, Coordinate coord);

	public Link createLink(String name, Node startNode, Node endNode,
			Coordinate[] coords);

	public Lane createLane(Link link, double start, double end, double width,
			double shift, int laneId);

	public List<Lane> createLanes(Link link, int num);

	public Connector createConnector(Lane laneFrom, Lane laneTo);

}
