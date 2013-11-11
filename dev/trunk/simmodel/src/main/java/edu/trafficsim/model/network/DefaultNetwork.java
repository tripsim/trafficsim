package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.vividsolutions.jts.geom.Coordinate;

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
		double x = 0, y = 0;
		for (Node node : nodes.values()) {
			x += node.getPoint().getX();
			y += node.getPoint().getY();
			if (node.getDownstreams().isEmpty())
				sinks.add(node);
			if (node.getUpstreams().isEmpty())
				sources.add(node);
		}
		double n = (double) nodes.size();
		center = new Coordinate(x / n, y / n);
	}

	@Override
	public Coordinate center() {
		return center;
	}

}
