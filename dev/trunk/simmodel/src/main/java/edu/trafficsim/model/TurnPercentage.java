package edu.trafficsim.model;

import java.util.Collection;

public interface TurnPercentage extends Composition<Link> {

	Node getNode();

	Link getUpstream();

	Collection<Link> getDownstreams();

	void remove(Link link);
}
