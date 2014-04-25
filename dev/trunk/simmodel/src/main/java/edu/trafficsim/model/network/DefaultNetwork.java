package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Node;

public class DefaultNetwork extends AbstractNetwork<DefaultNetwork> {

	private static final long serialVersionUID = 1L;

	public DefaultNetwork(long id, String name) {
		super(id, name);
	}

	private boolean dirty = true;
	private final Set<Node> sources = new HashSet<Node>();
	private final Set<Node> sinks = new HashSet<Node>();
	private Coordinate center = null;

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
		sources.clear();
		sinks.clear();
		double x = 0, y = 0, n = (double) nodes.size();
		if (n == 0)
			return;

		for (Node node : nodes.values()) {
			x += node.getPoint().getX();
			y += node.getPoint().getY();
			if (node.getDownstreams().isEmpty())
				sinks.add(node);
			if (node.getUpstreams().isEmpty())
				sources.add(node);
			if (node.getUpstreams().size() == 1
					&& node.getDownstreams().size() == 1
					&& node.getUpstreams().iterator().next() == node
							.getDownstreams().iterator().next()
							.getReverseLink()) {
				sinks.add(node);
				sources.add(node);
			}
		}
		center = new Coordinate(x / n, y / n);
		dirty = false;
	}

	@Override
	public Coordinate center() {
		return center;
	}

	@Override
	public boolean isSource(Node node) {
		return sources.contains(node);
	}

	@Override
	public boolean isSink(Node node) {
		return sinks.contains(node);
	}

	@Override
	public void dirty() {
		dirty = true;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

}
