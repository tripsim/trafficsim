package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

public interface Node extends Location {

	public Long getId();
	
	public String getName();
	
	public NodeType getNodeType();

	public boolean upstream(Link link);

	public boolean downstream(Link link);

	public Collection<Link> getUpstreams();

	public Collection<Link> getDownstreams();

	public Collection<Connector> getConnectors(Lane lane);
	
	public Connector getConnector(Lane fromLane, Link toLink);

	public void add(Link link) throws ModelInputException;

	public void add(Connector connector) throws ModelInputException;
}
