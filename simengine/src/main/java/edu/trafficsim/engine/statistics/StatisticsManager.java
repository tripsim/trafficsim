package edu.trafficsim.engine.statistics;

public interface StatisticsManager {

	void insertSnapshot(StatisticsSnapshot snapshot);

	StatisticsFrames<VehicleState> loadSnapshots(String simulationName,
			long startFrame, long steps);

	StatisticsFrames<VehicleState> loadSnapshots(String simulationName,
			long vid, long startFrame, long steps);

	StatisticsFrames<LinkState> loadLinkSnapshots(String simulationName,
			long linkId, long startFrame, long steps);

	StatisticsFrames<NodeState> loadNodeSnapshots(String simulationName,
			long nodeId, long startFrame, long steps);
}
