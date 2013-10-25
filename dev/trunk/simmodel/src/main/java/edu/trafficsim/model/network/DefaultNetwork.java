package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;

public class DefaultNetwork extends AbstractNetwork<DefaultNetwork> implements
		Network {

	private static final long serialVersionUID = 1L;

	public DefaultNetwork(long id, String name) {
		super(id, name);
	}

	private final Set<Node> sources = new HashSet<Node>();
	private final Set<Node> sinks = new HashSet<Node>();

	@Override
	public Collection<Node> getSources() {
		return Collections.unmodifiableCollection(sources);
	}

	@Override
	public Collection<Node> getSinks() {
		return Collections.unmodifiableCollection(sinks);
	}

	@Override
	public void discover() {
		for (Node node : nodes.values()) {
			if (node.getDownstreams().isEmpty())
				sinks.add(node);
			if (node.getUpstreams().isEmpty())
				sources.add(node);
		}
	}

}
