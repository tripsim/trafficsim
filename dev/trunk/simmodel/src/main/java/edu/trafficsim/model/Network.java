package edu.trafficsim.model;

import java.util.Collection;

import com.vividsolutions.jts.geom.Coordinate;

public interface Network {

	Collection<Node> getSources();

	Collection<Node> getSinks();

	Collection<Node> getNodes();

	Collection<Link> getLinks();

	Node getNode(long id);

	Link getLink(long id);

	boolean contains(Node node);

	boolean contains(Link link);

	boolean containsNode(long id);

	boolean containsLink(long id);

	void add(Node node);

	void add(Link link);

	Node removeNode(long id);

	Link removeLink(long id);

	Node removeNode(Node node);

	Link removeLink(Link link);

	void add(Node... nodes);

	void add(Link... links);

	Coordinate center();

	boolean isSource(Node node);

	boolean isSink(Node node);

	void dirty();

	boolean isDirty();

	/**
	 * discover its sources and sinks
	 */
	void discover();

	// TODO implement as needed

	// Set<Route> getRoutes(Node start, Node end);
	//
	// static Set<Route> getRoutes(Node start, Node end);
	//
	// static Set<Link> getDownstreamLinks(Node node);
	//
	// static Set<Link> getDownstreamLinks(Link link);
	//
	// static Set<Node> getDownstreamNodes(Node node);
	//
	// static Set<Node> getDownstreamNodes(Link link);
	//
	// static Set<Link> getUpstreamLinks(Node node);
	//
	// static Set<Link> getUpstreamLinks(Link link);
	//
	// static Set<Node> getUpstreamNodes(Node node);
	//
	// static Set<Node> getUpstreamNodes(Link link);
	//
	// static Network getDownstreamSubnetwork(Node node);
	//
	// static Network getDownstreamSubnetwork(Link link);
	//
	// static Network getUpstreamSubnetwork(Node node);
	//
	// static Network getUpstreamSubnetwork(Link link);
}
