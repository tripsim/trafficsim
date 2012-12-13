package edu.trafficsim.model.network;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.Coord;

public class Node extends AbstractLocation<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NodeType type;
	
	public Node(NodeType type, String name, Coord coord) {
		super(coord);
		setName(name);
		this.type = type;
	}

	public NodeType getType() {
		return type;
	}
	
	public void getNodeType(NodeType type) {
		this.type = type;
	}

}
