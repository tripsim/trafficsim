package edu.trafficsim.engine.statistics;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Vehicle;

class LinkState {

	final double time;

	final long id;
	final Map<Long, Double> speeds;
	final Map<Long, Double> positions;

	LinkState(double time, Link link) {
		this.time = time;
		id = link.getId();
		speeds = new HashMap<Long, Double>();
		positions = new HashMap<Long, Double>();
	}

	void update(Vehicle vehicle) {
		speeds.put(vehicle.getId(), vehicle.speed());
		positions.put(vehicle.getId(), vehicle.position());
	}
}
