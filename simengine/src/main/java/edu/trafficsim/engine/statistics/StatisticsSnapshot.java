package edu.trafficsim.engine.statistics;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.Vehicle;

class StatisticsSnapshot {

	final long startTimestamp;
	final long sequence;
	final double simulatedTime;

	Map<Long, VehicleSnapshot> vehicles = new HashMap<Long, VehicleSnapshot>();

	StatisticsSnapshot(long startTimestamp, long sequence, double simulatedTime) {
		this.startTimestamp = startTimestamp;
		this.sequence = sequence;
		this.simulatedTime = simulatedTime;
	}

	void visitVehicle(Vehicle vehicle) {
		VehicleSnapshot s = new VehicleSnapshot(vehicle);
		vehicles.put(s.linkId, s);
	}
}
