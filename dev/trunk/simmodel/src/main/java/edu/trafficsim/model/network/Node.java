package edu.trafficsim.model.network;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.Coord;

public class Node extends AbstractLocation<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NodeType nodeType;
	
	public Node(NodeType nodeType, String name, Coord coord) {
		super(coord);
		setName(name);
		this.nodeType = nodeType;
	}

	public NodeType getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

}
