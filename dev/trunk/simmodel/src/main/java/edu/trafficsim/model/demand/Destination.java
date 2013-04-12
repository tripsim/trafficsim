package edu.trafficsim.model.demand;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.network.Node;

public class Destination extends BaseEntity<Destination> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Node node;

	public Destination() {
	}

	public Destination(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public boolean isFree() {
		return node == null;
	}
}
