package edu.trafficsim.model.demand;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.network.Node;

public class Terminator extends BaseEntity<Terminator> implements Destination {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Node node;
	
	public Terminator(Node node) {
		this.node = node;
	}
	
	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public boolean isFree() {
		return node == null;
	}
}
