package edu.trafficsim.model.network;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.Position;

public class Node extends AbstractLocation<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Node(String name, Position position) {
		super(position);
		setName(name);
	}


}
