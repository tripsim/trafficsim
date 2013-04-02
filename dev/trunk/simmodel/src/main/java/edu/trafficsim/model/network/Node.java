package edu.trafficsim.model.network;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.ModelInputException;

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
	
	private Set<Link> outLinks = new HashSet<Link>();
	private Set<Link> inLinks = new HashSet<Link>();
	
	// First key is the upstream Link, and the second key is the downstream link
	// TODO revisit and reconsider movement info
	private final Map<Link, Map<Link, Integer>> turnImpediment  = new HashMap<Link, Map<Link,  Integer>>();
	// TODO lane connection information
	
	// TODO Store the geometry for the link edge
//	private Set<LineString> edges;
	
	public Node(String name, NodeType nodeType, Point point) {
		super(point);
		setName(name);
		this.nodeType = nodeType;
	}
	
	public short getMovement(Link fromLink, Link toLink) {
		return turnImpediment.get(fromLink).get(toLink).shortValue();
	}
	
	public void addLink(Link link) throws ModelInputException  {
		if (link.getFromNode() == this)
			outLinks.add(link);
		else if (link.getToNode() == this)
			inLinks.add(link);
		else
			throw new ModelInputException("The link doesnt start or end from the node.");
	}
	
	public void removeLink(Link link) {
		inLinks.remove(link);
		outLinks.remove(link);
	}
	
	public Set<Link> getOutLinks() {
		return Collections.unmodifiableSet(outLinks);
	}
	
	public Set<Link> getInLinks() {
		return Collections.unmodifiableSet(inLinks);
	}
	
	public NodeType getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
}
