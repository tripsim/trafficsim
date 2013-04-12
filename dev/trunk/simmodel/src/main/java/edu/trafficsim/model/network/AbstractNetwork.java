package edu.trafficsim.model.network;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractNetwork<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Set<Node> nodes = new HashSet<Node>();
	protected final Set<Link> links = new HashSet<Link>();

	public AbstractNetwork() {
	}

	public Set<Node> getAllNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	public Set<Link> getAllLinks() {
		return Collections.unmodifiableSet(links);
	}

	public boolean contains(Node node) {
		return nodes.contains(node);
	}

	public boolean contains(Link link) {
		return links.contains(link);
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void addLink(Link link) {
		links.add(link);
	}

	public void addNodes(Node... nodes) {
		for (Node node : nodes)
			addNode(node);
	}

	public void addLinks(Link... links) {
		for (Link link : links)
			addLink(link);
	}
	
}
