package edu.trafficsim.model.network;

import java.util.Collections;
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

	protected final Set<Node> nodes;
	protected final Set<Link> links;
	
	public AbstractNetwork() {
		nodes = new HashSet<Node>();
		links = new HashSet<Link>();	
	}
	
	@Override
	public Set<Node> getAllNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	@Override
	public Set<Link> getAllLinks() {
		return Collections.unmodifiableSet(links);
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
	public void addNode(Node node) {
		nodes.add(node);
	}

	@Override
	public void addLink(Link link) {
		links.add(link);
	}

	@Override
	public void addNodes(Node... nodes) {
		for (Node node : nodes)
			addNode(node);
	}

	@Override
	public void addLinks(Link... links) {
		for (Link link : links)
			addLink(link);
	}

	@Override
	public Set<Route> getRoutes(Node start, Node end) {
		// TODO Auto-generated method stub
		return null;
	}
}
