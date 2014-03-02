package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.AbstractLocation;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiValuedMap;

public abstract class AbstractNode<T> extends AbstractLocation<T> implements
		Node {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_INITIAL_CONNECTOR_MAP_CAPACITY = 4;

	private final Set<Link> downstreams = new HashSet<Link>();
	private final Set<Link> upstreams = new HashSet<Link>();

	// TODO may not need to maps
	private final MultiValuedMap<Lane, ConnectionLane> inConnectors = new MultiValuedMap<Lane, ConnectionLane>(
			DEFAULT_INITIAL_CONNECTOR_MAP_CAPACITY);
	private final MultiValuedMap<Lane, ConnectionLane> outConnectors = new MultiValuedMap<Lane, ConnectionLane>(
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

	@Override
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
		allConnectors.addAll(inConnectors.values());
		allConnectors.addAll(outConnectors.values());
		return Collections.unmodifiableCollection(allConnectors);
	}

	@Override
	public Collection<ConnectionLane> getInConnectors(Lane fromLane) {
		return inConnectors.get(fromLane);
	}

	@Override
	public Collection<ConnectionLane> getOutConnectors(Lane toLane) {
		return outConnectors.get(toLane);
	}

	@Override
	public Collection<ConnectionLane> getInConnectors(Link fromLink) {
		List<ConnectionLane> allConnectors = new ArrayList<ConnectionLane>();
		for (Lane lane : fromLink.getLanes()) {
			allConnectors.addAll(inConnectors.get(lane));
		}
		return allConnectors;
	}

	@Override
	public Collection<ConnectionLane> getOutConnectors(Link toLink) {
		List<ConnectionLane> allConnectors = new ArrayList<ConnectionLane>();
		for (Lane lane : toLink.getLanes()) {
			allConnectors.addAll(outConnectors.get(lane));
		}
		return allConnectors;
	}

	@Override
	public ConnectionLane getConnector(Lane fromLane, Lane toLane) {
		for (ConnectionLane connector : inConnectors.get(fromLane)) {
			if (connector.getToLane() == toLane)
				return connector;
		}
		return null;
	}

	@Override
	public Collection<ConnectionLane> getConnectors(Lane fromLane, Link toLink) {
		List<ConnectionLane> newConnectors = new ArrayList<ConnectionLane>();
		for (ConnectionLane connector : inConnectors.get(fromLane)) {
			if (connector.getToLane().getLink() == toLink)
				newConnectors.add(connector);
		}
		return Collections.unmodifiableCollection(newConnectors);
	}

	@Override
	public void add(ConnectionLane connector) {
		inConnectors.add(connector.getFromLane(), connector);
		outConnectors.add(connector.getToLane(), connector);
	}

	@Override
	public void remove(ConnectionLane connector) {
		inConnectors.remove(connector.getFromLane(), connector);
		outConnectors.remove(connector.getToLane(), connector);
	}

	@Override
	public boolean isConnected(Lane fromLane, Lane toLane) {
		return getConnector(fromLane, toLane) != null;
	}

	@Override
	public boolean isEmpty() {
		return upstreams.isEmpty() ? downstreams.isEmpty() ? true : false
				: false;
	}

	@Override
	public void onGeomUpdated() throws TransformException {
		// TODO trimming lane linear geom if necessary (Coordinates.trimxxxx)
	}
}
