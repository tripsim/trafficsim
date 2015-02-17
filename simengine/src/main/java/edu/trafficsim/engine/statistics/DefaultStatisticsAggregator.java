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
	public StatisticsFrames<VehicleState> getVehicleStates(long simulationId,
			long startFrame, long endFrame) {
		return statisticsManager.loadSnapshots(simulationId, startFrame,
				endFrame);
	}

	@Override
	public StatisticsFrames<VehicleState> getVehicleTrajectories(
			long simulationId, long vid, long startFrame, long endFrame) {
		return statisticsManager.loadSnapshots(simulationId, vid, startFrame,
				endFrame);
	}

	@Override
	public StatisticsFrames<LinkState> getLinkState(long simulationId,
			long linkId, long startFrame, long endFrame) {
		return statisticsManager.loadLinkSnapshots(simulationId, linkId,
				startFrame, endFrame);
	}

	@Override
	public StatisticsFrames<NodeState> getNodeState(long simulationId,
			long nodeId, long startFrame, long endFrame) {
		return statisticsManager.loadNodeSnapshots(simulationId, nodeId,
				startFrame, endFrame);
	}

}
