package edu.trafficsim.model;

import java.util.Collection;


public interface TurnPercentage extends Composition<Link> {

	public Node getNode();

	public Link getUpstream();

	public Collection<Link> getDownstreams();
}
