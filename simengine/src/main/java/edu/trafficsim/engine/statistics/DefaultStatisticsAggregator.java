package edu.trafficsim.engine.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.simulation.SimulationManager;

@Service("default-statistics-aggregator")
class DefaultStatisticsAggregator implements StatisticsAggregator {

	@Autowired
	StatisticsManager statisticsManager;
	@Autowired
	SimulationManager simulationManager;

	@Override
	public StatisticsFrames<VehicleState> getVehicleStates(
			String simulationName, long startFrame, long endFrame) {
		return statisticsManager.loadSnapshots(simulationName, startFrame,
				endFrame);
	}

	@Override
	public StatisticsFrames<VehicleState> getVehicleTrajectories(
			String simulationName, long vid, long startFrame, long endFrame) {
		return statisticsManager.loadSnapshots(simulationName, vid, startFrame,
				endFrame);
	}

	@Override
	public StatisticsFrames<LinkState> getLinkState(String simulationName,
			long linkId, long startFrame, long endFrame) {
		return statisticsManager.loadLinkSnapshots(simulationName, linkId,
				startFrame, endFrame);
	}

	@Override
	public StatisticsFrames<NodeState> getNodeState(String simulationName,
			long nodeId, long startFrame, long endFrame) {
		return statisticsManager.loadNodeSnapshots(simulationName, nodeId,
				startFrame, endFrame);
	}

}
