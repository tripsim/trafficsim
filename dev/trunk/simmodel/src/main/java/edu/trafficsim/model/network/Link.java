package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;

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
	
	private final List<Lane> lanes;
	private LineString leftEdge;
	private LineString rightEdge;
	
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
	
	public double getLength() {
		// TODO: work on it.
		return 0;
	}
	
	public double getWidth() {
		// TODO: work on it.
		return 0;
	}
	
	public LineString getLeftEdge() {
		return leftEdge;
	}
	
	public LineString getRightEdge() {
		return rightEdge;
	}

}
