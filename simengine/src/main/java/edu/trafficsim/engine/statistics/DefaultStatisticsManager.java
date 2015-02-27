package edu.trafficsim.engine.statistics;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.persistence.StatisticsSnapshotDao;
import edu.trafficsim.data.persistence.VehicleDao;

@Service("default-statistics-manager")
class DefaultStatisticsManager implements StatisticsManager {

	@Autowired
	StatisticsSnapshotDao statisticsSnapshotDao;
	@Autowired
	VehicleDao vehicleDao;

	@Override
	public void insertSnapshot(StatisticsSnapshot snapshot) {
		List<StatisticsSnapshotDo> entities = StatisticsAggregator
				.toSnapshotDo(snapshot);
		statisticsSnapshotDao.save(entities);
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
						getVehiclsFromNode(simulationName, nodeId), startFrame,
						steps);
		StatisticsFrames<VehicleState> frames = StatisticsAggregator
				.toVehicleStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<LinkState> getLinkStatistics(String simulationName,
			long linkId, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, linkId, startFrame, steps);
		StatisticsFrames<LinkState> frames = StatisticsAggregator
				.toLinkStates(snapshots);
		setSimulationInfo(simulationName, frames);
		return frames;
	}

	@Override
	public StatisticsFrames<NodeState> getNodeStatistics(String simulationName,
			long nodeId, long startFrame, long steps) {
		List<StatisticsSnapshotDo> snapshots = statisticsSnapshotDao
				.loadSnapshots(simulationName, nodeId, startFrame, steps);
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
	public List<Long> getVehiclsFromNode(String simulationName, long nodeId) {
		return vehicleDao.findVehicleIdsFrom(simulationName, nodeId);
	}
}
