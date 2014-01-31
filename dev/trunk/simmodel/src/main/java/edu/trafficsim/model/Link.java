package edu.trafficsim.model;

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

	public ConnectionLane[] getConnectionLane(Link destLink);

	public void add(ConnectionLane lane, Link destLink);

	// TODO
	// public void remove(ConnectionLane lane);

	public Link getReverseLink();

	public void setReverseLink(Link reverseLink);

	public void removeReverseLink();

	public RoadInfo getRoadInfo();

	public void setRoadInfo(RoadInfo roadInfo);

}
