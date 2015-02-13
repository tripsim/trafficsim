package edu.trafficsim.engine.statistics;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.Vehicle;

class StatisticsSnapshot {

	double time;

	Map<Long, VehicleSnapshot> vehicles = new HashMap<Long, VehicleSnapshot>();

	StatisticsSnapshot(double time) {
		this.time = time;
	}

	void visitVehicle(Vehicle vehicle) {
		VehicleSnapshot s = new VehicleSnapshot(vehicle);
		vehicles.put(s.linkId, s);
	}
}
