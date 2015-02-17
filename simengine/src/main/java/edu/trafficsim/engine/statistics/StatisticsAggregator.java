package edu.trafficsim.engine.statistics;

public interface StatisticsAggregator {

	StatisticsFrames<VehicleState> getVehicleStates(long simulationId,
			long startFrame, long endFrame);

	StatisticsFrames<VehicleState> getVehicleTrajectories(long simulationId,
			long vid, long startFrame, long endFrame);

	StatisticsFrames<LinkState> getLinkState(long simulationId, long linkId,
			long startFrame, long endFrame);

	StatisticsFrames<NodeState> getNodeState(long simulationId, long nodeId,
			long startFrame, long endFrame);
}
