package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.ModelInputException;

public abstract class AbstractNode<T> extends AbstractLocation<T> implements
		Node {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_INITIAL_CONNECTOR_MAP_CAPACITY = 4;
	private static final int DEFAULT_INITIAL_CONNECTOR_LIST_CAPACITY = 4;

	private final Set<Link> downstreams = new HashSet<Link>();
	private final Set<Link> upstreams = new HashSet<Link>();

	// TODO may not need to maps
	private final Map<Lane, List<ConnectionLane>> inConnectors = new HashMap<Lane, List<ConnectionLane>>(
			DEFAULT_INITIAL_CONNECTOR_MAP_CAPACITY);
	private final Map<Lane, List<ConnectionLane>> outConnectors = new HashMap<Lane, List<ConnectionLane>>(
			DEFAULT_INITIAL_CONNECTOR_MAP_CAPACITY);

	public AbstractNode(long id, String name, Point point, double radius) {
		super(id, name, point, radius);
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
	public Collection<ConnectionLane> getConnectors() {
		List<ConnectionLane> allConnectors = new ArrayList<ConnectionLane>();
		for (List<ConnectionLane> connectors : inConnectors.values()) {
			allConnectors.addAll(connectors);
		}
		for (List<ConnectionLane> connectors : outConnectors.values()) {
			allConnectors.addAll(connectors);
		}
		return Collections.unmodifiableCollection(allConnectors);
	}

	@Override
	public Collection<ConnectionLane> getInConnectors(Lane fromLane) {
		if (inConnectors.get(fromLane) != null)
			return Collections.unmodifiableCollection(inConnectors
					.get(fromLane));
		return Collections.emptyList();
	}

	@Override
	public Collection<ConnectionLane> getOutConnectors(Lane toLane) {
		if (outConnectors.get(toLane) != null)
			return Collections
					.unmodifiableCollection(outConnectors.get(toLane));
		return Collections.emptyList();
	}

	@Override
	public ConnectionLane getConnector(Lane fromLane, Lane toLane) {
		if (inConnectors.get(fromLane) != null) {
			for (ConnectionLane connector : inConnectors.get(fromLane)) {
				if (connector.getToLane() == toLane)
					return connector;
			}
		}
		return null;
	}

	@Override
	public Collection<ConnectionLane> getConnectors(Lane fromLane, Link toLink) {
		List<ConnectionLane> newConnectors = new ArrayList<ConnectionLane>();
		if (inConnectors.get(fromLane) != null) {
			for (ConnectionLane connector : inConnectors.get(fromLane)) {
				if (connector.getToLane().getLink() == toLink)
					newConnectors.add(connector);
			}
		}
		return Collections.unmodifiableCollection(newConnectors);
	}

	@Override
	public void add(ConnectionLane connector) {
		List<ConnectionLane> connectors = inConnectors.get(connector
				.getFromLane());
		if (connectors == null) {
			connectors = new ArrayList<ConnectionLane>(
					DEFAULT_INITIAL_CONNECTOR_LIST_CAPACITY);
			inConnectors.put(connector.getFromLane(), connectors);
		}
		connectors.add(connector);

		connectors = outConnectors.get(connector.getToLane());
		if (connectors == null) {
			connectors = new ArrayList<ConnectionLane>(
					DEFAULT_INITIAL_CONNECTOR_LIST_CAPACITY);
			outConnectors.put(connector.getToLane(), connectors);
		}
		connectors.add(connector);
	}

	@Override
	public void remove(ConnectionLane connector) {
		List<ConnectionLane> connectors = inConnectors.get(connector
				.getFromLane());
		if (connectors != null)
			connectors.remove(connector);

		connectors = outConnectors.get(connector.getToLane());
		if (connectors != null)
			connectors.remove(connector);
	}

	@Override
	public boolean isConnected(Lane fromLane, Lane toLane) {
		return getConnector(fromLane, toLane) != null;
	}

	@Override
	public void onGeomUpdated() throws TransformException {
		// TODO trimming lane linear geom if necessary (Coordinates.trimxxxx)
	}
}
