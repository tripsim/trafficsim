package edu.trafficsim.api.model;

public interface LinkEditListener {

	void onLaneAdded(Lane lane);

	void onLaneRemoved(int lanePosition, Lane lane);
}
