package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.plugin.ILink;

public class Link extends AbstractSegment<Link> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// make variable in the future
	public static final double NEIBOUR_SIZE = 100.0;
	
	private ILink impl;
	
	private LinkType linkType;
	private List<Lane> lanes;
	
	public Link(LinkType linkType, String name, Node fromNode, Node toNode) {
		super(fromNode, toNode);
		lanes = new ArrayList<Lane>();
		setName(name);
		this.linkType = linkType;
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


	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

}
