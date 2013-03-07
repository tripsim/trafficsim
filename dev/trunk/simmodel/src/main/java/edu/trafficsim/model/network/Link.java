package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
	
	private final Map<Byte, Lane> lanes;
	
	private LineString centerLine;
	// TODO use edge representation to fit in the map exactly
//	private LineString leftEdge;
//	private LineString rightEdge;
	
	public Link(String name, LinkType linkType, Node fromNode, Node toNode, LineString centerLine) {
		super(fromNode, toNode);
		
		setName(name);
		this.centerLine = centerLine;
		this.linkType = linkType;
		
		lanes = new HashMap<Byte, Lane>();
	}
	
	
	public Node getFromNode() {
		return (Node) getFromLocation();
	}
	
	
	public Node getToNode() {
		return (Node) getToLocation();
	}
	
	public Lane getLane(byte laneId) {
		return lanes.get(laneId);
	}
	
	public Lane addLane(Lane lane) {
		return lanes.put(lane.getLaneId(), lane);
	}
	
	public Collection<Lane> getLanes() {
		return Collections.unmodifiableCollection(lanes.values());
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
		// TODO: work on it.
		return 0;
	}
	
//	public LineString getLeftEdge() {
//		return leftEdge;
//	}
//	
//	public LineString getRightEdge() {
//		return rightEdge;
//	}

}
