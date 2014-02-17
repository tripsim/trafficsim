package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

public interface Node extends Location {

	public NodeType getNodeType();

	public boolean upstream(Link link);

	public boolean downstream(Link link);

	public Collection<Link> getUpstreams();

	public Collection<Link> getDownstreams();

	public Collection<ConnectionLane> getConnectors();

	public ConnectionLane getConnector(Lane fromLane, Lane toLane);

	// TODO make connectors cached in links
	public Collection<ConnectionLane> getInConnectors(Lane fromLane);

	public Collection<ConnectionLane> getOutConnectors(Lane toLane);

	public Collection<ConnectionLane> getConnectors(Lane fromLane, Link toLink);

	public boolean isConnected(Lane fromLane, Lane toLane);

	public void add(Link link) throws ModelInputException;

	public void add(ConnectionLane connector) throws ModelInputException;

	public void remove(ConnectionLane connector);
}
