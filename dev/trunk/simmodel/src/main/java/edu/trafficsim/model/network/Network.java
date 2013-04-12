package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.CoordinateFilter;

import edu.trafficsim.model.demand.Origin;
import edu.trafficsim.model.demand.Router;

public class Network extends AbstractNetwork<Network> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Set<Node> sources = new HashSet<Node>();
	private final Set<Node> sinks = new HashSet<Node>();

	public final List<Origin> origins = new ArrayList<Origin>();
	public final Map<Link, Router> routers = new HashMap<Link, Router>();

	public Network() {
	}

	public Set<Node> getAllSources() {
		return sources;
	}

	public Set<Node> getAllSinks() {
		return sinks;
	}

	public List<Origin> getOrigins() {
		return origins;
	}

	public void add(Origin origin) {
		origins.add(origin);
	}

	public Router getRouter(Navigable upstream) {
		return routers.get(upstream);
	}

	public void add(Router router) {
		routers.put(router.getUpstream(), router);
	}

	public void discover() {
		for (Node node : nodes) {
			if (node.getDownstreams().isEmpty())
				sinks.add(node);
			if (node.getUpstreams().isEmpty())
				sources.add(node);
		}
	}

	public void transform(CoordinateFilter filter) {
		for (Link link : getAllLinks()) {
			link.getLinearGeom().apply(filter);
		}
		for (Node node : getAllNodes()) {
			node.getPoint().apply(filter);
			for (Connector connector : node.getConnectors())
				connector.getLinearGeom().apply(filter);
		}
		// TODO transform units properly as well
	}

	// public Set<Route> getRoutes(Node start, Node end);

	// TODO implement as needed
	//
	// public static Set<Route> getRoutes(Node start, Node end);
	//
	// public static Set<Link> getDownstreamLinks(Node node);
	//
	// public static Set<Link> getDownstreamLinks(Link link);
	//
	// public static Set<Node> getDownstreamNodes(Node node);
	//
	// public static Set<Node> getDownstreamNodes(Link link);
	//
	// public static Set<Link> getUpstreamLinks(Node node);
	//
	// public static Set<Link> getUpstreamLinks(Link link);
	//
	// public static Set<Node> getUpstreamNodes(Node node);
	//
	// public static Set<Node> getUpstreamNodes(Link link);
	//
	// public static Network getDownstreamSubnetwork(Node node);
	//
	// public static Network getDownstreamSubnetwork(Link link);
	//
	// public static Network getUpstreamSubnetwork(Node node);
	//
	// public static Network getUpstreamSubnetwork(Link link);
}
