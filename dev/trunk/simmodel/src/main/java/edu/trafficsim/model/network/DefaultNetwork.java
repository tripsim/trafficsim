package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.vividsolutions.jts.geom.CoordinateFilter;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;

public class DefaultNetwork extends AbstractNetwork<DefaultNetwork> implements
		Network {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Set<Node> sources = new HashSet<Node>();
	private final Set<Node> sinks = new HashSet<Node>();

	public final Set<Od> ods = new HashSet<Od>();

	@Override
	public Collection<Node> getSources() {
		return Collections.unmodifiableCollection(sources);
	}

	@Override
	public Collection<Node> getSinks() {
		return Collections.unmodifiableCollection(sinks);
	}

	@Override
	public Collection<Od> getOds() {
		return Collections.unmodifiableCollection(ods);
	}

	public void add(Od od) {
		ods.add(od);
	}

	@Override
	public void discover() {
		for (Node node : nodes) {
			if (node.getDownstreams().isEmpty())
				sinks.add(node);
			if (node.getUpstreams().isEmpty())
				sources.add(node);
		}
	}

	@Override
	public void transform(CoordinateFilter filter) {
		for (Link link : getLinks())
			link.transform(filter);
		for (Node node : getNodes())
			node.transform(filter);
	}

}
