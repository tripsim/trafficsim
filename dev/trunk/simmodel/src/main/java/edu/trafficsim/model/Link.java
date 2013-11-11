package edu.trafficsim.model;

import java.util.List;

public interface Link extends Segment {

	public LinkType getLinkType();

	public Node getStartNode();

	public Node getEndNode();

	public Lane getLane(int index);

	public List<Lane> getLanes();

	public void add(Lane lane);

	public Link getReverseLink();

	public void setReverseLink(Link reverseLink);

	public void removeReverseLink();

	public RoadInfo getRoadInfo();

	public void setRoadInfo(RoadInfo roadInfo);

}
