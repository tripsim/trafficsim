package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractNetwork<T> extends BaseEntity<T> implements
		Network {

	private static final long serialVersionUID = 1L;

	protected final Set<Node> nodes = new HashSet<Node>();
	protected final Set<Link> links = new HashSet<Link>();

	public AbstractNetwork() {
	}

	@Override
	public Collection<Node> getNodes() {
		return Collections.unmodifiableCollection(nodes);
	}

	@Override
	public Collection<Link> getLinks() {
		return Collections.unmodifiableCollection(links);
	}

	public boolean contains(Node node) {
		return nodes.contains(node);
	}

	public boolean contains(Link link) {
		return links.contains(link);
	}

	public void add(Node node) {
		nodes.add(node);
	}

	public void add(Link link) {
		links.add(link);
	}

	public void add(Node... nodes) {
		for (Node node : nodes)
			add(node);
	}

	public void add(Link... links) {
		for (Link link : links)
			add(link);
	}

}
