package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.model.core.AbstractSegment;

public class Link extends AbstractSegment<Link> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Lane> lanes;
	
	public Link(String name, Node fromNode, Node toNode) {
		super(fromNode, toNode);
		lanes = new ArrayList<Lane>();
		setName(name);
	}
	
	
	public Node getFromNode() {
		return (Node) getFromLocation();
	}
	
	
	public Node getToNode() {
		return (Node) getToLocation();
	}
	
	public List<Lane> getLanes() {
		return lanes;
	}

}
