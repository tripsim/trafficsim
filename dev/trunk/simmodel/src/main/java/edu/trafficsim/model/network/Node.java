package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;

public class Node extends AbstractLocation<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final short MOVEMENT_RESTRICTED = -1;
	public static final short MOVEMENT_RIGHT = 1;
	public static final short MOVEMENT_THROUGH = 2;
	public static final short MOVEMENT_LEFT = 3;
	public static final short MOVEMENT_UTURN = 4;

	private NodeType nodeType;
	// TODO introduce implementation
//	private INode nodeImpl;
	
	private Set<Link> links;
	
	// First key is the upstream Link, and the second key is the downstream link
	// TODO revisit and reconsider movement info
	private final Map<Link, Map<Link, Integer>> turnImpediment;
	// TODO lane connection information
	
	// TODO Store the geometry for the link edge
//	private Set<LineString> edges;
	
	public Node(String name, NodeType nodeType, Point point) {
		super(point);
		setName(name);
		this.nodeType = nodeType;
		
		links = new HashSet<Link>();
		
		turnImpediment = new HashMap<Link, Map<Link,  Integer>>();
	}
	
	public short getMovement(Link fromLink, Link toLink) {
		return turnImpediment.get(fromLink).get(toLink).shortValue();
	}
	
	public void addLink(Link link) {
		if (link.getFromNode() == this || link.getToNode() == this)
			links.add(link);
	}
	
	public void Link(Link link) {
		links.remove(link);
	}
	
	public Set<Link> getLinks() {
		return Collections.unmodifiableSet(links);
	}
	
	public List<Lane> getOutLanes() {
		List<Lane> lanes = new ArrayList<Lane>();
		for (Link link : links) {
			if (link.getFromNode() == this)
				lanes.addAll(link.getForwardLanes());
			else
				lanes.addAll(link.getBackwardLanes());
		}
		return lanes;
	}
	
	public List<Lane> getInLanes() {
		List<Lane> lanes = new ArrayList<Lane>();
		for (Link link : links) {
			if (link.getFromNode() == this)
				lanes.addAll(link.getBackwardLanes());
			else
				lanes.addAll(link.getForwardLanes());
		}
		return lanes;
	}
	
	public NodeType getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
}
