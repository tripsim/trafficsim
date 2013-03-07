package edu.trafficsim.model.network;

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
	
	// TODO make variable in the future
	public static final double FRAGMENT_SIZE = 100.0;
	
	// TODO introduce implementation
//	private ILink impl;
	private LinkType linkType;
	
	private final List<Lane> forwardLanes;
	private final List<Lane> backwardLanes;
	
	private LineString centerLine;
	// TODO use edge representation to fit in the map exactly
//	private LineString leftEdge;
//	private LineString rightEdge;
	
	public Link(String name, LinkType linkType, Node fromNode, Node toNode, LineString centerLine) {
		super(fromNode, toNode);
		
		setName(name);
		this.centerLine = centerLine;
		this.linkType = linkType;
		
		forwardLanes = new LinkedList<Lane>();
		backwardLanes = new LinkedList<Lane>();
	}
	
	
	public Node getFromNode() {
		return (Node) getFromLocation();
	}
	
	
	public Node getToNode() {
		return (Node) getToLocation();
	}

	public void addForwardLane(int index, Lane lane) {
		forwardLanes.add(index, lane);
	}
	
	public void addBackwardLane(int index, Lane lane) {
		backwardLanes.add(index, lane);
	}
	
	public void addForwardLane(Lane lane) {
		forwardLanes.add(lane);
	}
	
	public void addBackwardLane(Lane lane) {
		backwardLanes.add(lane);
	}
	
	public Lane getForwardLane(int laneId) {
		return forwardLanes.get(laneId);
	}
	
	public Lane getBackwardLane(int laneId) {
		return backwardLanes.get(laneId);
	}
	
	public List<Lane> getForwardLanes() {
		return Collections.unmodifiableList(forwardLanes);
	}
	
	public List<Lane> getBackwardLanes() {
		return Collections.unmodifiableList(backwardLanes);
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
		for (Lane lane : forwardLanes)
			width += lane.getWidth();
		for (Lane lane : backwardLanes)
			width += lane.getWidth();
		return width;
	}
	
//	public LineString getLeftEdge() {
//		return leftEdge;
//	}
//	
//	public LineString getRightEdge() {
//		return rightEdge;
//	}

}
