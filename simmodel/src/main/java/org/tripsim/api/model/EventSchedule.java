package org.tripsim.api.model;

import org.tripsim.api.Behavior;

public interface EventSchedule extends Behavior {

	double getStartTime();

	double getEndTime();

}
