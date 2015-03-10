package org.tripsim.engine.statistics;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.data.dom.StatisticsSnapshotDo;
import org.tripsim.data.dom.VehicleDo;
import org.tripsim.data.persistence.StatisticsSnapshotDao;
import org.tripsim.data.persistence.VehicleDao;

@Service("default-statistics-manager")
class DefaultStatisticsManager implements StatisticsManager {

	@Autowired
	StatisticsSnapshotDao statisticsSnapshotDao;
	@Autowired
	VehicleDao vehicleDao;

	@Override
	public void insertSnapshot(Snapshot snapshot) {
		insertStatisticsSnapshot(snapshot);
		insertVehicleProperty(snapshot);
	}

	private void insertStatisticsSnapshot(Snapshot snapshot) {
		List<StatisticsSnapshotDo> entities = StatisticsAggregator
				.toStatisticsSnapshotDos(snapshot);
		statisticsSnapshotDao.save(entities);
	}

	private void insertVehicleProperty(Snapshot snapshot) {
		List<VehicleDo> entities = StatisticsAggregator.toVehicleDos(snapshot);
		vehicleDao.save(entities);
	}

	@Override
	public StatisticsFrames<VehicleState> getVehicleStatistics(
			String simulationName, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, startFrame, steps);
		StatisticsFrames<VehicleState> frames = StatisticsAggregator
				.toVehicleStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<VehicleState> getVehicleStatistics(
			String simulationName, long vid, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, vid, startFrame, steps);
		StatisticsFrames<VehicleState> frames = StatisticsAggregator
				.toVehicleStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<VehicleState> getTrajectoriesFromNode(
			String simulationName, long nodeId, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName,
						getVehiclesFromNode(simulationName, nodeId),
						startFrame, steps);
		StatisticsFrames<VehicleState> frames = StatisticsAggregator
				.toVehicleStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<LinkState> getLinkStatistics(String simulationName,
			long linkId, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshotsByLink(simulationName, linkId, startFrame, steps);
		StatisticsFrames<LinkState> frames = StatisticsAggregator
				.toLinkStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<NodeState> getNodeStatistics(String simulationName,
			long nodeId, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshotsByNode(simulationName, nodeId, startFrame, steps);
		StatisticsFrames<NodeState> frames = StatisticsAggregator
				.toNodeStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	private void setSimulationInfo(String simulationName,
			StatisticsFrames<?> frames) {
		frames.simulationName = simulationName;
		// set simulation settings info
	}

	@Override
	public List<VehicleProperty> getVehicleProperties(String simulationName) {
		return StatisticsAggregator.toVehicleProperties(vehicleDao
				.loadVehicles(simulationName));
	}

	@Override
	public List<VehicleProperty> getVehicleProperties(String simulationName,
			Collection<Long> vids) {
		return StatisticsAggregator.toVehicleProperties(vehicleDao
				.loadVehicles(simulationName, vids));
	}

	@Override
	public List<Long> getVehiclesFromNode(String simulationName, long nodeId) {
		return vehicleDao.findVehicleIdsFrom(simulationName, nodeId);
	}
}
