package edu.trafficsim.engine.statistics;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.Vehicle;

class StatisticsSnapshot {

	final String simulationName;
	final long sequence;
	final double simulatedTime;

	Map<Long, VehicleSnapshot> vehicles = new HashMap<Long, VehicleSnapshot>();

	StatisticsSnapshot(String simulationName, long sequence, double simulatedTime) {
		this.simulationName = simulationName;
		this.sequence = sequence;
		this.simulatedTime = simulatedTime;
	}

	void visitVehicle(Vehicle vehicle) {
		VehicleSnapshot s = new VehicleSnapshot(vehicle);
		vehicles.put(s.linkId, s);
	}

	@Override
	public String toString() {
		return "StatisticsSnapshot [simulationName=" + simulationName + ", sequence="
				+ sequence + ", simulatedTime=" + simulatedTime + ", vehicles="
				+ vehicles + "]";
	}

}
