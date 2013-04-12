package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiKey;

public class Node extends AbstractLocation<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public static final short MOVEMENT_RESTRICTED = -1;
	// public static final short MOVEMENT_RIGHT = 1;
	// public static final short MOVEMENT_THROUGH = 2;
	// public static final short MOVEMENT_LEFT = 3;
	// public static final short MOVEMENT_UTURN = 4;

	protected static final class ConnectorKey extends MultiKey<Lane, Lane> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ConnectorKey(Lane fromLane, Lane toLane) {
			super(fromLane, toLane);
		}

	}

	private NodeType nodeType;
	// TODO introduce implementation
	// private INode nodeImpl;

	private Set<Link> downstreams = new HashSet<Link>();
	private Set<Link> upstreams = new HashSet<Link>();

	private final Map<ConnectorKey, Connector> connectors = new HashMap<ConnectorKey, Connector>();

	public Node(String name, NodeType nodeType, Point point, double radius) {
		super(point, radius);
		setName(name);
		this.nodeType = nodeType;
	}

	public final void addLink(Link link) throws ModelInputException {
		if (link.getEndNode() == this)
			upstreams.add(link);
		else if (link.getStartNode() == this)
			downstreams.add(link);
		else
			throw new ModelInputException(
					"The link doesnt start or end from the node.");
	}

	public final void removeLink(Link link) {
		upstreams.remove(link);
		downstreams.remove(link);
	}

	public final boolean isUpstream(Link link) {
		return upstreams.contains(link);
	}

	public final boolean isDownstream(Link link) {
		return downstreams.contains(link);
	}

	public final Connector getConnector(Lane fromLane, Lane toLane) {
		return connectors.get(new ConnectorKey(fromLane, toLane));
	}

	public final void addConnector(Connector connector) {
		connectors
				.put(new ConnectorKey(connector.getFromLane(), connector
						.getToLane()), connector);
	}

	public final void removeConnector(Lane fromLane, Lane toLane) {
		connectors.remove(new ConnectorKey(fromLane, toLane));
	}

	public final Collection<Connector> getConnectors() {
		return Collections.unmodifiableCollection(connectors.values());
	}

	public final Set<Link> getUpstreams() {
		return Collections.unmodifiableSet(upstreams);
	}

	public final Set<Link> getDownstreams() {
		return Collections.unmodifiableSet(downstreams);
	}

	public final NodeType getNodeType() {
		return nodeType;
	}

	public final void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
}
