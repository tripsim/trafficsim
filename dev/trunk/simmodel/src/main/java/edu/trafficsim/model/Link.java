package edu.trafficsim.model;

import java.util.Collection;

import org.opengis.referencing.operation.TransformException;

public interface Link extends Segment {

	LinkType getLinkType();

	Node getStartNode();

	Node getEndNode();

	Lane getLane(int index);

	Lane[] getLanes();

	int numOfLanes();

	void add(Lane lane) throws TransformException;

	void remove(int laneId) throws TransformException;

	Collection<ConnectionLane> getConnectors(Link destLink);

	Collection<ConnectionLane> getToConnectors();

	Collection<ConnectionLane> getFromConnectors();

	Link getReverseLink();

	void setReverseLink(Link reverseLink);

	void removeReverseLink();

	RoadInfo getRoadInfo();

	void setRoadInfo(RoadInfo roadInfo);

}
