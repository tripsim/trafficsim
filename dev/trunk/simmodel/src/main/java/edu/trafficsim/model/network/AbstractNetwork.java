package edu.trafficsim.model.network;

import java.util.HashSet;
import java.util.Set;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.Navigable;

public abstract class AbstractNetwork<T> extends BaseEntity<T> implements
		Navigable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Set<Node> nodes;
	private final Set<Link> links;
	
	public AbstractNetwork() {
		nodes = new HashSet<Node>();
		links = new HashSet<Link>();	
	}
	
	@Override
	public Set<Node> getAllNodes() {
		return nodes;
	}

	@Override
	public Set<Link> getAllLinks() {
		return links;
	}

	@Override
	public boolean contains(Node node) {
		return nodes.contains(node);
	}

	@Override
	public boolean contains(Link link) {
		return links.contains(link);
	}

	@Override
	public Set<Route> getRoutes(Node start, Node end) {
		// TODO Auto-generated method stub
		return null;
	}
}
