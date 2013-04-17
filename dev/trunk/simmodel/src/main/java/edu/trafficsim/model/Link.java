package edu.trafficsim.model;

import java.util.List;

import edu.trafficsim.model.network.LinkType;

public interface Link extends Segment {

	public LinkType getLinkType();

	public Node getStartNode();

	public Node getEndNode();

	public Lane getLane(int index);
	
	public List<Lane> getLanes();

	public void add(Lane lane);
	
	public Link getReverseLink();
}
