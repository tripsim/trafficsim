package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

public interface Node extends Location {

	NodeType getNodeType();

	boolean upstream(Link link);

	boolean downstream(Link link);

	Link getToNode(Node node);

	Link getFromNode(Node node);

	Collection<Link> getUpstreams();

	Collection<Link> getDownstreams();

	Collection<ConnectionLane> getConnectors();

	ConnectionLane getConnector(Lane fromLane, Lane toLane);

	// TODO make connectors cached in links
	Collection<ConnectionLane> getInConnectors(Lane fromLane);

	Collection<ConnectionLane> getOutConnectors(Lane toLane);

	Collection<ConnectionLane> getConnectors(Lane fromLane, Link toLink);

	boolean isConnected(Lane fromLane, Lane toLane);

	void add(Link link) throws ModelInputException;

	void add(ConnectionLane connector) throws ModelInputException;

	void remove(Link link);

	void remove(ConnectionLane connector);

	Collection<ConnectionLane> getOutConnectors(Link toLink);

	Collection<ConnectionLane> getInConnectors(Link fromLink);

	boolean isEmpty();
}
