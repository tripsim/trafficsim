package edu.trafficsim.api.model;

import edu.trafficsim.api.Behavior;

public interface EventSchedule extends Behavior {

	double getStartTime();

	double getEndTime();

}
