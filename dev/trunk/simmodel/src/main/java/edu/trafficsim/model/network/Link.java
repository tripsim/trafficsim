package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.core.AbstractSegment;

public class Link extends AbstractSegment<Link> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// TODO introduce implementation
//	private ILink impl;
	private LinkType linkType;
	
	private final List<Lane> lanes =  new LinkedList<Lane>();
	
	private Link reverseLink;
	// If reverse link is null it is the cernter line of the link
	// otherwise it is the center line of the 
	private LineString centerLine;
	// TODO use edge representation to fit in the map exactly
//	private LineString leftEdge;
//	private LineString rightEdge;
	
	public Link(String name, LinkType linkType, Node fromNode, Node toNode, LineString centerLine) {
		super(fromNode, toNode);
		
		setName(name);
		this.centerLine = centerLine;
		this.linkType = linkType;
	}
	
	public static final Link createReverseLink (Link link, String name) {
		Link newLink = new Link(name, link.getLinkType(), link.getToNode(), link.getFromNode(), (LineString) link.getCenterLine().reverse());
		newLink.reverseLink = link;
		link.reverseLink = newLink;
		return newLink;
	}
	
	public Node getFromNode() {
		return (Node) getFromLocation();
	}
	
	
	public Node getToNode() {
		return (Node) getToLocation();
	}

	public void addLane(int index, Lane lane) {
		lanes.add(index, lane);
	}
	
	public void addLane(Lane lane) {
		lanes.add(lane);
	}
	
	public void addLanes(Collection<Lane> lanes) {
		lanes.addAll(lanes);
	}
	
	
	public Lane getLane(int laneId) {
		return lanes.get(laneId);
	}
	
	// make it sorted when loading
	public List<Lane> getLanes() {
		return Collections.unmodifiableList(lanes);
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}
	
	public LineString getCenterLine() {
		return centerLine;
	}
	
	public double getLength() {
		return centerLine.getLength();
	}
	
	public double getWidth() {
		double width = 0;
		for (Lane lane : lanes)
			width += lane.getWidth();
		return width;
	}
	
	public Link getReverseLink() {
		return reverseLink;
	}
	
	public void removeReverseLink() {
		reverseLink.reverseLink = null;
		reverseLink = null;
	}
	
//	public LineString getLeftEdge() {
//		return leftEdge;
//	}
//	
//	public LineString getRightEdge() {
//		return rightEdge;
//	}

}
