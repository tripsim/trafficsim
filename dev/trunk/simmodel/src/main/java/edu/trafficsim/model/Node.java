package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.NodeType;

public interface Node extends Location {

	public NodeType getNodeType();

	public boolean upstream(Link link);

	public boolean downstream(Link link);

	public Collection<Link> getUpstreams();

	public Collection<Link> getDownstreams();

	public Collection<Connector> getConnectors(Lane lane);

	public Router getRouter();

	public void setRouter(Router router);

	public void add(Link link) throws ModelInputException;

	public void add(Connector connector) throws ModelInputException;
}
