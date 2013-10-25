package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractNetwork<T> extends BaseEntity<T> implements
		Network {

	private static final long serialVersionUID = 1L;

	protected final Map<Long, Node> nodes = new HashMap<Long, Node>();
	protected final Map<Long, Link> links = new HashMap<Long, Link>();

	public AbstractNetwork(long id, String name) {
		super(id, name);
	}

	@Override
	public Collection<Node> getNodes() {
		return Collections.unmodifiableCollection(nodes.values());
	}

	@Override
	public Collection<Link> getLinks() {
		return Collections.unmodifiableCollection(links.values());
	}

	@Override
	public boolean contains(Node node) {
		return nodes.values().contains(node);
	}

	@Override
	public boolean contains(Link link) {
		return links.values().contains(link);
	}

	public boolean containsNode(long id) {
		return nodes.get(id) == null;
	}

	public boolean containsLink(long id) {
		return links.get(id) == null;
	}

	@Override
	public Node getNode(long id) {
		return nodes.get(id);
	}

	@Override
	public Link getLink(long id) {
		return links.get(id);
	}

	@Override
	public void add(Node node) {
		nodes.put(node.getId(), node);
	}

	@Override
	public void add(Link link) {
		links.put(link.getId(), link);
	}

	@Override
	public void add(Node... nodes) {
		for (Node node : nodes)
			add(node);
	}

	@Override
	public void add(Link... links) {
		for (Link link : links)
			add(link);
	}

}
