package edu.trafficsim.model.network;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.plugin.INode;

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
	private INode nodeImpl;
	
	// First key is the upstream Link, and the second key is the downstream link
	private final Map<Link, Map<Link, Integer>> turnImpediment;
	
	// Store the geometry for the link edge
	private Set<LineString> edges;
	
	public Node(NodeType nodeType, String name) {
		setName(name);
		this.nodeType = nodeType;
		turnImpediment = new HashMap<Link, Map<Link,  Integer>>();
	}
	
	public short getMovement(Link fromLink, Link toLink) {
		return turnImpediment.get(fromLink).get(toLink).shortValue();
	}
	
	public Set<Link> getOutLinks() {
		return Collections.unmodifiableSet(new HashSet<Link>());
	}
	
	public Set<Link> getInLinks() {
		return Collections.unmodifiableSet(new HashSet<Link>());
	}
	
	public NodeType getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
}
