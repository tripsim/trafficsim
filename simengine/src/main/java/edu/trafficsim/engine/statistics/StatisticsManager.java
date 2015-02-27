package edu.trafficsim.engine.statistics;

import java.util.Collection;
import java.util.List;

public interface StatisticsManager {

	void insertSnapshot(Snapshot snapshot);

	StatisticsFrames<VehicleState> getVehicleStatistics(String simulationName,
			long startFrame, long steps);

	StatisticsFrames<VehicleState> getVehicleStatistics(String simulationName,
			long vid, long startFrame, long steps);

	StatisticsFrames<VehicleState> getTrajectoriesFromNode(
			String simulationName, long nodeId, long startFrame, long steps);

	StatisticsFrames<LinkState> getLinkStatistics(String simulationName,
			long linkId, long startFrame, long steps);

	StatisticsFrames<NodeState> getNodeStatistics(String simulationName,
			long nodeId, long startFrame, long steps);

	List<VehicleProperty> getVehicleProperties(String simulationName);

	List<VehicleProperty> getVehicleProperties(String simulationName,
			Collection<Long> vids);

	List<Long> getVehiclesFromNode(String simulationName, long nodeId);
}
