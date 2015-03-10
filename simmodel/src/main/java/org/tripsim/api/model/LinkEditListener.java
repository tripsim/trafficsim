package org.tripsim.api.model;

public interface LinkEditListener {

	void onLaneAdded(Lane lane);

	void onLaneRemoved(int lanePosition, Lane lane);
}
