package edu.trafficsim.model;

import java.util.Collection;

import com.vividsolutions.jts.geom.CoordinateFilter;


public interface Network {

	public Collection<Node> getSources();

	public Collection<Node> getSinks();

	public Collection<Node> getNodes();
	
	public Collection<Link> getLinks();

	public Collection<Od> getOds();

	/**
	 * refresh its sources and sinks
	 */
	public void discover();

	/**
	 * @param filter
	 *            which is used to transform all the coordinates of the
	 *            components in the network
	 */
	public void transform(CoordinateFilter filter);

	// TODO implement as needed

	// public Set<Route> getRoutes(Node start, Node end);
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
