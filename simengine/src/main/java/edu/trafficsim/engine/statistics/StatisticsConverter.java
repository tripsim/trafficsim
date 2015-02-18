package edu.trafficsim.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;

final class StatisticsConverter {

	static List<StatisticsSnapshotDo> toSnapshotDo(StatisticsSnapshot snapshot) {
		List<StatisticsSnapshotDo> result = new ArrayList<StatisticsSnapshotDo>();
		for (VehicleSnapshot vs : snapshot.vehicles.values()) {
			StatisticsSnapshotDo ssd = new StatisticsSnapshotDo();
			ssd.setSequence(snapshot.sequence);
			ssd.setName(snapshot.outcomeName);
			applyVehicleStatisticsDo(ssd, vs);
		}
		return result;
	}

	static void applyVehicleStatisticsDo(StatisticsSnapshotDo ssd,
			VehicleSnapshot vs) {
		ssd.setVid(vs.vid);
		ssd.setLat(vs.coord.y);
		ssd.setLon(vs.coord.x);
		ssd.setPosition(vs.position);
		ssd.setAngle(vs.angle);
		ssd.setSpeed(vs.speed);
		ssd.setAccel(vs.accel);
		ssd.setLinkId(vs.linkId);
		ssd.setNodeId(vs.nodeId);
	}

	static StatisticsFrames<VehicleState> toVehicleStates(
			List<StatisticsSnapshotDo> snapshots) {
		StatisticsFrames<VehicleState> frames = new StatisticsFrames<VehicleState>();
		for (StatisticsSnapshotDo snapshot : snapshots) {
			VehicleState vs = toVehicleState(snapshot);
			frames.addState(vs.sequence, vs);
		}
		return frames;
	}

	protected static VehicleState toVehicleState(StatisticsSnapshotDo snapshot) {
		VehicleState vs = new VehicleState();
		vs.setSequence(snapshot.getSequence());
		vs.setVid(snapshot.getVid());
		vs.setLat(snapshot.getLat());
		vs.setLon(snapshot.getLon());
		vs.setPosition(snapshot.getPosition());
		vs.setAngle(snapshot.getAngle());
		vs.setSpeed(snapshot.getSpeed());
		vs.setAccel(snapshot.getAccel());
		return vs;
	}

	static StatisticsFrames<LinkState> toLinkStates(
			List<StatisticsSnapshotDo> snapshots) {
		StatisticsFrames<LinkState> frames = new StatisticsFrames<LinkState>();
		for (StatisticsSnapshotDo snapshot : snapshots) {
			applyLinkState(frames, snapshot);
		}
		return frames;
	}

	protected static void applyLinkState(StatisticsFrames<LinkState> frames,
			StatisticsSnapshotDo snapshot) {
		LinkState ls = frames.getState(snapshot.getSequence());
		if (ls == null) {
			frames.addState(
					snapshot.getSequence(),
					ls = new LinkState(snapshot.getSequence(), snapshot
							.getLinkId()));
		}
		ls.update(snapshot.getVid(), snapshot.getSpeed(), snapshot.getAccel());
	}

	static StatisticsFrames<NodeState> toNodeStates(
			List<StatisticsSnapshotDo> snapshots) {
		StatisticsFrames<NodeState> frames = new StatisticsFrames<NodeState>();
		for (StatisticsSnapshotDo snapshot : snapshots) {
			applyNodeState(frames, snapshot);
		}
		return frames;
	}

	protected static void applyNodeState(StatisticsFrames<NodeState> frames,
			StatisticsSnapshotDo snapshot) {
		NodeState ns = frames.getState(snapshot.getSequence());
		if (ns == null) {
			frames.addState(
					snapshot.getSequence(),
					ns = new NodeState(snapshot.getSequence(), snapshot
							.getLinkId()));
		}
		ns.update(snapshot.getVid(), snapshot.getSpeed(), snapshot.getAccel());
	}
}
