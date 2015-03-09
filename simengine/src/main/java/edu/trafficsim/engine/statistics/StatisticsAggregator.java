package edu.trafficsim.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.dom.VehicleDo;

final class StatisticsAggregator {

	// --------------------------------------------------
	// to Entities
	// --------------------------------------------------
	static List<StatisticsSnapshotDo> toStatisticsSnapshotDos(Snapshot snapshot) {
		List<StatisticsSnapshotDo> result = new ArrayList<StatisticsSnapshotDo>();
		for (VehicleSnapshot vs : snapshot.vehicles) {
			StatisticsSnapshotDo ssd = new StatisticsSnapshotDo();
			ssd.setSequence(snapshot.sequence);
			ssd.setSimulationName(snapshot.simulationName);
			applyVehicleStatisticsDo(ssd, vs);
			result.add(ssd);
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

	static List<VehicleDo> toVehicleDos(Snapshot snapshot) {
		List<VehicleDo> entities = new ArrayList<VehicleDo>(
				snapshot.newVehicles.size());
		for (VehicleProperty vehicle : snapshot.newVehicles) {
			VehicleDo entity = toVehicleDo(vehicle);
			entity.setSimulationName(snapshot.simulationName);
			entities.add(entity);
		}
		return entities;
	}

	private static VehicleDo toVehicleDo(VehicleProperty vehicle) {
		VehicleDo entity = new VehicleDo();
		entity.setVid(vehicle.getVid());
		entity.setInitFrame(vehicle.getInitFrame());
		entity.setStartNodeId(vehicle.getStartNodeId());
		entity.setDestinationNodeId(vehicle.getDestinationNodeId());
		entity.setWidth(vehicle.getWidth());
		entity.setLength(vehicle.getLength());
		entity.setHeight(vehicle.getLength());
		return entity;
	}

	// --------------------------------------------------
	// to Statistics
	// --------------------------------------------------
	static StatisticsFrames<VehicleState> toVehicleStates(
			List<StatisticsSnapshotDo> snapshots) {
		StatisticsFrames<VehicleState> frames = new StatisticsFrames<VehicleState>();
		for (StatisticsSnapshotDo snapshot : snapshots) {
			VehicleState vs = toVehicleState(snapshot);
			frames.addState(vs.getVid(), vs.sequence, vs);
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
		LinkState ls = frames.getState(snapshot.getLinkId(),
				snapshot.getSequence());
		if (ls == null) {
			frames.addState(
					snapshot.getLinkId(),
					snapshot.getSequence(),
					ls = new LinkState(snapshot.getSequence(), snapshot
							.getLinkId()));
		}
		ls.update(snapshot.getVid(), snapshot.getSpeed(),
				snapshot.getPosition());
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
		NodeState ns = frames.getState(snapshot.getNodeId(),
				snapshot.getSequence());
		if (ns == null) {
			frames.addState(
					snapshot.getNodeId(),
					snapshot.getSequence(),
					ns = new NodeState(snapshot.getSequence(), snapshot
							.getLinkId()));
		}
		ns.update(snapshot.getVid(), snapshot.getSpeed(),
				snapshot.getPosition());
	}

	static List<VehicleProperty> toVehicleProperties(List<VehicleDo> vehicles) {
		List<VehicleProperty> result = new ArrayList<VehicleProperty>(
				vehicles.size());
		for (VehicleDo vehicle : vehicles) {
			result.add(toVehicleProperty(vehicle));
		}
		return result;
	}

	private static VehicleProperty toVehicleProperty(VehicleDo vehicle) {
		VehicleProperty property = new VehicleProperty(vehicle.getVid(),
				vehicle.getInitFrame(), vehicle.getStartNodeId());
		property.setWidth(vehicle.getWidth());
		property.setLength(vehicle.getLength());
		property.setHeight(vehicle.getHeight());
		return property;
	}
}
