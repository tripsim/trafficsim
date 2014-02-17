package edu.trafficsim.model;

import java.util.Collection;

import org.opengis.referencing.operation.TransformException;

public interface Link extends Segment {

	public LinkType getLinkType();

	public Node getStartNode();

	public Node getEndNode();

	public Lane getLane(int index);

	public Lane[] getLanes();

	public int numOfLanes();

	public void add(Lane lane) throws TransformException;

	public void remove(int laneId) throws TransformException;

	public Collection<ConnectionLane> getConnectionLanes(Link destLink);

	public Link getReverseLink();

	public void setReverseLink(Link reverseLink);

	public void removeReverseLink();

	public RoadInfo getRoadInfo();

	public void setRoadInfo(RoadInfo roadInfo);

}
