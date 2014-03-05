package edu.trafficsim.model;

public interface RoadInfo extends DataContainer {

	long getRoadId();

	void setRoadId(long osmId);

	String getHighway();

	void setHighway(String highway);

}
