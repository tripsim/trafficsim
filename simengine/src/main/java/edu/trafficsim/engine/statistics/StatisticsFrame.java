package edu.trafficsim.engine.statistics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StatisticsFrame {
	
	double time;
	Map<Long, VehicleState> vehicleStates;
	Map<Long, LinkState> linkStates;
	Map<Long, NodeState> nodeStates;

	public StatisticsFrame(double time) {
		this.time = time;
		vehicleStates = new HashMap<Long, VehicleState>();
		linkStates = new HashMap<Long, LinkState>();
	}

	public double getTime() {
		return time;
	}

	public VehicleState getVehicleState(Long vid) {
		return vehicleStates.get(vid);
	}

	public Collection<Long> getVehicleIds() {
		return vehicleStates.keySet();
	}

	public LinkState getLinkState(Long id) {
		return linkStates.get(id);
	}
	
	public Collection<Long> getLinkIds() {
		return linkStates.keySet();
	}

}
