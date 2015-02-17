package edu.trafficsim.engine.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.persistence.StatisticsSnapshotDao;

@Service("default-statistics-manager")
public class DefaultStatisticsManager implements StatisticsManager {

	@Autowired
	StatisticsSnapshotDao statisticsSnapshotDao;

	@Override
	public void insertSnapshot(StatisticsSnapshot snapshot) {
		List<StatisticsSnapshotDo> entities = StatisticsConverter
				.toSnapshotDo(snapshot);
		statisticsSnapshotDao.save(entities);
	}

	@Override
	public StatisticsFrames<VehicleState> loadSnapshots(long simulationId,
			long startFrame, long endFrame) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationId, startFrame, endFrame);
		StatisticsFrames<VehicleState> frames = StatisticsConverter
				.toVehicleStates(snapshots);
		applySimulationInfo(simulationId, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<VehicleState> loadSnapshots(long simulationId,
			long vid, long startFrame, long endFrame) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationId, vid, startFrame, endFrame);
		StatisticsFrames<VehicleState> frames = StatisticsConverter
				.toVehicleStates(snapshots);
		applySimulationInfo(simulationId, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<LinkState> loadLinkSnapshots(long simulationId,
			long linkId, long startFrame, long endFrame) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationId, linkId, startFrame, endFrame);
		StatisticsFrames<LinkState> frames = StatisticsConverter
				.toLinkStates(snapshots);
		applySimulationInfo(simulationId, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<NodeState> loadNodeSnapshots(long simulationId,
			long nodeId, long startFrame, long endFrame) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationId, nodeId, startFrame, endFrame);
		StatisticsFrames<NodeState> frames = StatisticsConverter
				.toNodeStates(snapshots);
		applySimulationInfo(simulationId, frames);
		return frames;
	}

	private void applySimulationInfo(long simulationId,
			StatisticsFrames<?> frames) {
		frames.simulationId = simulationId;
		// load simulation settings info
	}
}
