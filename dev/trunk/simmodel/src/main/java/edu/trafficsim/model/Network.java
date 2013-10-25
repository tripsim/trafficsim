package edu.trafficsim.model;

import java.util.Collection;

public interface Network {

	public Collection<Node> getSources();

	public Collection<Node> getSinks();

	public Collection<Node> getNodes();

	public Collection<Link> getLinks();

	public Node getNode(long id);

	public Link getLink(long id);

	public boolean contains(Node node);

	public boolean contains(Link link);

	public boolean containsNode(long id);

	public boolean containsLink(long id);

	public void add(Node node);

	public void add(Link link);

	public void add(Node... nodes);

	public void add(Link... links);

	/**
	 * discover its sources and sinks
	 */
	public void discover();

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
