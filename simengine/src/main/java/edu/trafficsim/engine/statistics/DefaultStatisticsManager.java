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
	public StatisticsFrames<VehicleState> loadSnapshots(String simulationName,
			long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, startFrame, steps);
		StatisticsFrames<VehicleState> frames = StatisticsConverter
				.toVehicleStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<VehicleState> loadSnapshots(String simulationName,
			long vid, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, vid, startFrame, steps);
		StatisticsFrames<VehicleState> frames = StatisticsConverter
				.toVehicleStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<LinkState> loadLinkSnapshots(String simulationName,
			long linkId, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, linkId, startFrame, steps);
		StatisticsFrames<LinkState> frames = StatisticsConverter
				.toLinkStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<NodeState> loadNodeSnapshots(String simulationName,
			long nodeId, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, nodeId, startFrame, steps);
		StatisticsFrames<NodeState> frames = StatisticsConverter
				.toNodeStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	private void setSimulationInfo(String simulationName,
			StatisticsFrames<?> frames) {
		frames.simulationName = simulationName;
		// set simulation settings info
	}
}
