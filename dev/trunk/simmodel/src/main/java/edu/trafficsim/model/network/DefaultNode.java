package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.Subsegment;
import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.Coordinates;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultNode extends AbstractLocation<DefaultNode> implements Node {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_INITIAL_CONNECTOR_SET_CAPACITY = 2;

	// public static final short MOVEMENT_RESTRICTED = -1;
	// public static final short MOVEMENT_RIGHT = 1;
	// public static final short MOVEMENT_THROUGH = 2;
	// public static final short MOVEMENT_LEFT = 3;
	// public static final short MOVEMENT_UTURN = 4;

	private NodeType nodeType;

	private final Set<Link> downstreams = new HashSet<Link>();
	private final Set<Link> upstreams = new HashSet<Link>();

	private final Map<Lane, Set<ConnectionLane>> connectionLanes = new HashMap<Lane, Set<ConnectionLane>>();

	public DefaultNode(long id, String name, NodeType nodeType, Point point,
			double radius) {
		super(id, name, point, radius);
		this.nodeType = nodeType;
	}

	@Override
	public final void add(Link link) throws ModelInputException {
		if (link.getEndNode() == this)
			upstreams.add(link);
		else if (link.getStartNode() == this)
			downstreams.add(link);
		else
			throw new ModelInputException(
					"The link doesn't start or end from the node.");
	}

	public final void remove(Link link) {
		upstreams.remove(link);
		downstreams.remove(link);
	}

	@Override
	public final boolean upstream(Link link) {
		return upstreams.contains(link);
	}

	@Override
	public final boolean downstream(Link link) {
		return downstreams.contains(link);
	}

	@Override
	public final Collection<Link> getUpstreams() {
		return Collections.unmodifiableCollection(upstreams);
	}

	@Override
	public final Collection<Link> getDownstreams() {
		return Collections.unmodifiableCollection(downstreams);
	}

	@Override
	public final NodeType getNodeType() {
		return nodeType;
	}

	@Override
	public Collection<ConnectionLane> getConnectors(Lane fromLane) {
		return Collections.unmodifiableCollection(connectionLanes.get(fromLane));
	}

	@Override
	public ConnectionLane getConnector(Lane fromLane, Link toLink) {
		for (ConnectionLane connectionLane : connectionLanes.get(fromLane)) {
			if (connectionLane.getToLane().getSegment().equals(toLink))
				return connectionLane;
		}
		return null;
	}

	@Override
	public void add(ConnectionLane connectionLane) {
		Set<ConnectionLane> fromLaneConnectors = connectionLanes.get(connectionLane
				.getFromLane());
		if (fromLaneConnectors == null) {
			fromLaneConnectors = new HashSet<ConnectionLane>(
					DEFAULT_INITIAL_CONNECTOR_SET_CAPACITY);
			connectionLanes.put(connectionLane.getFromLane(), fromLaneConnectors);
		}
		fromLaneConnectors.add(connectionLane);
	}

	@Override
	public void onGeomUpdated() throws TransformException {
		for (Link link : upstreams) {
			for (Subsegment subsegment : link.getSubsegments())
				Coordinates.trimLinearGeom(subsegment);
		}
		for (Link link : downstreams) {
			for (Subsegment subsegment : link.getSubsegments())
				Coordinates.trimLinearGeom(subsegment);
		}
		// TODO ....
	}

}
