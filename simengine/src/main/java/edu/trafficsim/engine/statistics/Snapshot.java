package edu.trafficsim.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.api.model.Vehicle;

class Snapshot {

	final String simulationName;
	final long sequence;
	final double simulatedTime;

	List<VehicleSnapshot> vehicles = new ArrayList<VehicleSnapshot>();
	List<VehicleProperty> newVehicles = new ArrayList<VehicleProperty>();

	Snapshot(String simulationName, long sequence, double simulatedTime) {
		this.simulationName = simulationName;
		this.sequence = sequence;
		this.simulatedTime = simulatedTime;
	}

	void visitVehicle(Vehicle vehicle) {
		VehicleSnapshot s = new VehicleSnapshot(vehicle);
		vehicles.add(s);
	}

	void addNewVehicle(Vehicle vehicle) {
		VehicleProperty vp = new VehicleProperty(vehicle.getId(),
				vehicle.getStartFrame(), vehicle.getOrigin().getId());
		vp.setWidth(vehicle.getWidth());
		vp.setLength(vehicle.getLength());
		if (vehicle.getDestination() != null) {
			vp.setDestinationNodeId(vehicle.getDestination().getId());
		}
		newVehicles.add(vp);
	}

	@Override
	public String toString() {
		return "StatisticsSnapshot [simulationName=" + simulationName
				+ ", sequence=" + sequence + ", simulatedTime=" + simulatedTime
				+ ", vehicles=" + vehicles + ", newVehicles=" + newVehicles
				+ "]";
	}

}
