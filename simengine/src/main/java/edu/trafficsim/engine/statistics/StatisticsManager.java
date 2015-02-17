package edu.trafficsim.engine.statistics;

public interface StatisticsManager {

	void insertSnapshot(StatisticsSnapshot snapshot);

	StatisticsFrames<VehicleState> loadSnapshots(long simulationId,
			long startFrame, long endFrame);

	StatisticsFrames<VehicleState> loadSnapshots(long simulationId, long vid,
			long startFrame, long endFrame);

	StatisticsFrames<LinkState> loadLinkSnapshots(long simulationId,
			long linkId, long startFrame, long endFrame);

	StatisticsFrames<NodeState> loadNodeSnapshots(long simulationId,
			long nodeId, long startFrame, long endFrame);
}
