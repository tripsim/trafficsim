package edu.trafficsim.engine.statistics;

public interface StatisticsAggregator {

	StatisticsFrames<VehicleState> getVehicleStates(String simulationName,
			long startFrame, long endFrame);

	StatisticsFrames<VehicleState> getVehicleTrajectories(
			String simulationName, long vid, long startFrame, long endFrame);

	StatisticsFrames<LinkState> getLinkState(String simulationName,
			long linkId, long startFrame, long endFrame);

	StatisticsFrames<NodeState> getNodeState(String simulationName,
			long nodeId, long startFrame, long endFrame);
}
