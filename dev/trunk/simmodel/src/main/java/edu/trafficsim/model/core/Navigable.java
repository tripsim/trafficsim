package edu.trafficsim.model.core;

import java.util.Set;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.network.Route;

public interface Navigable extends DataContainer {
	
	public boolean contains(Node node);
	
	public boolean contains(Link link);

	public Set<Node> getAllNodes();
	
	public Set<Link> getAllLinks();
	
	public Set<Route> getRoutes(Node start, Node end);
	
// TODO put the followings in the utility
//	public Set<Link> getDownstreamLinks(Node node);
//	
//	public Set<Link> getDownstreamLinks(Link link);
//	
//	public Set<Node> getDownstreamNodes(Node node);
//
//	public Set<Node> getDownstreamNodes(Link link);
//	
//	public Set<Link> getUpstreamLinks(Node node);
//
//	public Set<Link> getUpstreamLinks(Link link);
//	
//	public Set<Node> getUpstreamNodes(Node node);
//	
//	public Set<Node> getUpstreamNodes(Link link);
//	
//	public Network getDownstreamSubnetwork(Node node);
//	
//	public Network getDownstreamSubnetwork(Link link);
//
//	public Network getUpstreamSubnetwork(Node node);
//	
//	public Network getUpstreamSubnetwork(Link link);
}
