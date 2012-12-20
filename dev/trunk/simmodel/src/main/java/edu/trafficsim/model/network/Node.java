package edu.trafficsim.model.network;

import java.util.HashMap;
import java.util.Map;

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

	private Map<Link, Map<Link, Integer>> turnTable;
	
	public Node(String name) {
		setName(name);
		turnTable = new HashMap<Link, Map<Link,  Integer>>();
	}
	
	public short getMovement(Link fromLink, Link toLink) {
		return turnTable.get(fromLink).get(toLink).shortValue();
	}
	
}
