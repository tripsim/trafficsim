package edu.trafficsim.model.network;

import java.util.HashSet;
import java.util.Set;

import edu.trafficsim.model.core.Navigable;

public class Network extends AbstractNetwork<Network> implements Navigable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Set<Node> sources;
	private final Set<Node> sinks;

	public Network() {
		sources = new HashSet<Node>();
		sinks = new HashSet<Node>();
	}
	
	public Set<Node> getAllSources() {
		return sources;
	}
	
	public Set<Node> getAllSinks() {
		return sinks;
	}
	
	public void discover() {
		for (Node node : nodes) {
			if (node.getOutLinks().isEmpty())
				sources.add(node);
			if (node.getInLinks().isEmpty())
				sinks.add(node);
		}
	}

}
