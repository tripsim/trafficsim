package edu.trafficsim.model.network;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.NodeType;

public class DefaultNode extends AbstractNode<DefaultNode> {

	private static final long serialVersionUID = 1L;

	// public static final short MOVEMENT_RESTRICTED = -1;
	// public static final short MOVEMENT_RIGHT = 1;
	// public static final short MOVEMENT_THROUGH = 2;
	// public static final short MOVEMENT_LEFT = 3;
	// public static final short MOVEMENT_UTURN = 4;

	private final static double DEFAULT_RADIUS = 0;

	private NodeType nodeType;

	public DefaultNode(long id, String name, NodeType nodeType, Point point) {
		super(id, name, point, DEFAULT_RADIUS);
		this.nodeType = nodeType;
	}

	@Override
	public final NodeType getNodeType() {
		return nodeType;
	}

}
