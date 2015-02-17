package edu.trafficsim.engine.statistics;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.Vehicle;

class StatisticsSnapshot {

	final double startedTimestamp;

	final double simulationTime;
	final long sequence;

	Map<Long, VehicleSnapshot> vehicles = new HashMap<Long, VehicleSnapshot>();

	StatisticsSnapshot(double startedTimestamp, double simulationTime,
			long sequence) {
		this.startedTimestamp = startedTimestamp;
		this.simulationTime = simulationTime;
		this.sequence = sequence;
	}

	void visitVehicle(Vehicle vehicle) {
		VehicleSnapshot s = new VehicleSnapshot(vehicle);
		vehicles.put(s.linkId, s);
	}
}
