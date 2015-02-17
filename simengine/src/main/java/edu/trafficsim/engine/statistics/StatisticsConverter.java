package edu.trafficsim.engine.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.dom.VehicleStatisticsDo;

final class StatisticsConverter {

	static StatisticsSnapshotDo toSnapshotDo(StatisticsSnapshot snapshot) {
		StatisticsSnapshotDo result = new StatisticsSnapshotDo();
		result.setSequence(snapshot.sequence);
		result.setSimulationId(snapshot.startedTimestamp);
		result.setVechicleStatistics(toVehicleStatisticsDos(snapshot.vehicles));
		return result;
	}

	static List<VehicleStatisticsDo> toVehicleStatisticsDos(
			Map<Long, VehicleSnapshot> vehicleSnapshots) {
		List<VehicleStatisticsDo> result = new ArrayList<VehicleStatisticsDo>(
				vehicleSnapshots.size());
		for (VehicleSnapshot snapshot : vehicleSnapshots.values()) {
			result.add(toVehicleStatisticsDo(snapshot));
		}
		return result;
	}

	static VehicleStatisticsDo toVehicleStatisticsDo(VehicleSnapshot stat) {
		VehicleStatisticsDo result = new VehicleStatisticsDo();
		result.setVid(stat.vid);
		result.setLat(stat.coord.x);
		result.setLon(stat.coord.y);
		result.setPosition(stat.position);
		result.setAngle(stat.angle);
		result.setSpeed(stat.speed);
		result.setAccel(stat.accel);
		result.setLinkId(stat.linkId);
		result.setNodeId(stat.nodeId);
		return result;
	}
}
