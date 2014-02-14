package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

public interface Node extends Location {
	
	public NodeType getNodeType();

	public boolean upstream(Link link);

	public boolean downstream(Link link);

	public Collection<Link> getUpstreams();

	public Collection<Link> getDownstreams();

	public Collection<ConnectionLane> getConnectors(Lane lane);
	
	public ConnectionLane getConnector(Lane fromLane, Link toLink);
	
	public ConnectionLane getConnector(Lane fromLane, Lane toLane);

	public void add(Link link) throws ModelInputException;

	public void add(ConnectionLane connectionLane) throws ModelInputException;
	
	public boolean isConnected(Lane fromLane, Lane toLane);
}
