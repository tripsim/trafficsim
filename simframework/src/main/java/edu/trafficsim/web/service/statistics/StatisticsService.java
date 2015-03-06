/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.web.service.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.statistics.LinkState;
import edu.trafficsim.engine.statistics.StatisticsFrames;
import edu.trafficsim.engine.statistics.StatisticsManager;
import edu.trafficsim.engine.statistics.VehicleProperty;
import edu.trafficsim.engine.statistics.VehicleState;
import edu.trafficsim.model.util.Colors;
import edu.trafficsim.util.MultiKeyedMap;
import edu.trafficsim.util.WktUtils;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service
public class StatisticsService {

	@Autowired
	StatisticsManager manager;
	@Autowired
	NetworkFactory factory;

	// --------------------------------------------------
	// Frames for animation
	// --------------------------------------------------
	public FramesDto getFrames(String simulationName, long startFrame,
			long steps) {
		StatisticsFrames<VehicleState> frames = manager.getVehicleStatistics(
				simulationName, startFrame, steps);
		FramesDto result = new FramesDto(startFrame);
		result.setElements(toFrameElements(frames));
		result.setVehicles(toFrameVehicles(simulationName, frames.getIds()));
		return result;
	}

	private List<String> toFrameVehicles(String simulationName,
			Collection<Long> vids) {
		List<VehicleProperty> vehicles = manager.getVehicleProperties(
				simulationName, vids);
		List<String> result = new ArrayList<String>();
		for (VehicleProperty vehicle : vehicles) {
			result.add(toFrameVehicle(vehicle));
		}
		return result;
	}

	private String toFrameVehicle(VehicleProperty property) {
		StringBuilder sb = new StringBuilder();
		sb.append(property.getVid());
		sb.append(",");
		sb.append(property.getWidth());
		sb.append(",");
		sb.append(property.getLength());
		return sb.toString();
	}

	private List<String> toFrameElements(StatisticsFrames<VehicleState> frames) {
		List<String> result = new ArrayList<String>();
		for (Long vid : frames.getIds()) {
			for (VehicleState vs : frames.getStatesById(vid)) {
				result.add(toFrameElement(vs));
			}
		}
		return result;
	}

	private String toFrameElement(VehicleState vs) {
		StringBuilder sb = new StringBuilder();
		sb.append(vs.getSequence());
		sb.append(",");
		sb.append(vs.getVid());
		sb.append(",");
		sb.append(vs.getLon());
		sb.append(",");
		sb.append(vs.getLat());
		sb.append(",");
		sb.append(vs.getAngle());
		sb.append(",");
		String color = Colors.getVehicleColor(vs.getSpeed());
		sb.append(color);
		return sb.toString();
	}

	// --------------------------------------------------
	// Trajectories for vehicle trajectories at each node
	// --------------------------------------------------
	public TrajectoriesDto getTrajectories(String simulationName, long nodeId,
			long startFrame, long steps) {
		StatisticsFrames<VehicleState> states = manager
				.getTrajectoriesFromNode(simulationName, nodeId, startFrame,
						steps);
		TrajectoriesDto result = new TrajectoriesDto(nodeId, startFrame);
		List<String> trajectories = new ArrayList<String>();
		result.setTrajectories(trajectories);
		for (long vid : states.getIds()) {
			trajectories.add(toTrajectory(states.getStatesById(vid)));
		}
		return result;
	}

	private String toTrajectory(Collection<VehicleState> states) {
		Coordinate[] coords = new Coordinate[states.size()];
		int i = 0;
		for (VehicleState vs : states) {
			Coordinate coord = new Coordinate(vs.getLon(), vs.getLat());
			coords[i] = coord;
			i++;
		}
		return WktUtils.toWKT(factory.createLineString(coords));
	}

	// --------------------------------------------------
	// Time-space diagram for each link
	// --------------------------------------------------
	public TsdDto getTsd(String simulationName, long linkId, long startFrame,
			long steps) {
		StatisticsFrames<LinkState> frames = manager.getLinkStatistics(
				simulationName, linkId, startFrame, steps);
		MultiKeyedMap<Long, Long, Double> data = toSeriesesData(frames
				.getStatesById(linkId));
		List<List<List<Number>>> serieses = toSerieses(data);
		TsdDto result = new TsdDto(linkId, startFrame);
		result.setSerieses(serieses);
		return result;
	}

	/**
	 * key1 -> vid, key2 -> sequence , value -> position
	 * 
	 * @param states
	 * @return
	 */
	private MultiKeyedMap<Long, Long, Double> toSeriesesData(
			Collection<LinkState> states) {
		MultiKeyedMap<Long, Long, Double> result = new MultiKeyedMap<Long, Long, Double>();
		for (LinkState ls : states) {
			long sequence = ls.getSequence();
			for (Map.Entry<Long, Double> entry : ls.getPositions().entrySet()) {
				long vid = entry.getKey();
				double position = entry.getValue();
				result.put(vid, sequence, position);
			}
		}
		return result;
	}

	private List<List<List<Number>>> toSerieses(
			MultiKeyedMap<Long, Long, Double> data) {
		List<List<List<Number>>> result = new ArrayList<List<List<Number>>>();
		for (long vid : data.getPrimayKeys()) {
			result.add(toSeries(data.getByPrimary(vid)));
		}
		return result;
	}

	private List<List<Number>> toSeries(Map<Long, Double> data) {
		List<List<Number>> result = new ArrayList<List<Number>>();
		for (Map.Entry<Long, Double> entry : data.entrySet()) {
			long sequence = entry.getKey();
			double position = entry.getValue();
			result.add(Arrays.asList((Number) sequence, (Number) position));
		}
		return result;
	}

	// --------------------------------------------------
	// Fundamental diagram for network
	// --------------------------------------------------
	public FdDto getFd(String simulationName, long startFrame, long steps) {
		FdDto fd = new FdDto();
		return fd;
	}

	// public String getTrajectory(StatisticsCollector statics,
	// NetworkFactory factory, Long vid) {
	// VehicleState[] vehicleStates = statics.trajectory(vid);
	// Coordinate[] coords = new Coordinate[vehicleStates.length];
	// for (int i = 0; i < vehicleStates.length; i++) {
	// coords[i] = vehicleStates[i].coord;
	// }
	// return mapJsonService.getWkt(factory.createLineString(coords));
	// }
	//
	// public String getTsdPlotData(StatisticsCollector statics, Long id) {
	// LinkState[] linkStats = statics.linkStats(id);
	// MultiValuedMap<Long, String> data = new MultiValuedMap<Long, String>();
	// for (int i = 0; i < linkStats.length; i++) {
	// for (Map.Entry<Long, Double> entry : linkStats[i].positions
	// .entrySet()) {
	// data.add(entry.getKey(),
	// "[" + linkStats[i].time + "," + entry.getValue() + "]");
	// }
	// }
	// StringBuffer sb = new StringBuffer();
	// for (Long vid : data.keys()) {
	// sb.append(data.get(vid));
	// sb.append(",");
	// }
	// if (sb.length() > 0)
	// sb.deleteCharAt(sb.length() - 1);
	// return "[" + sb.toString() + "]";
	// }
	//
	// public String getFrames(StatisticsCollector statics) {
	// StringBuffer vehicleSb = new StringBuffer();
	// StringBuffer frameSb = new StringBuffer();
	//
	// for (Map.Entry<Long, List<VehicleState>> entry : statics.trajectories()
	// .entrySet()) {
	//
	// Vehicle vehicle = statics.getVehicle(entry.getKey());
	//
	// String name = vehicle.getName();
	// int initFrameId = vehicle.getStartFrame();
	//
	// vehicleSb.append("\"");
	// vehicleSb.append(name);
	// vehicleSb.append(",");
	// vehicleSb.append(vehicle.getWidth());
	// vehicleSb.append(",");
	// vehicleSb.append(vehicle.getLength());
	// vehicleSb.append("\"");
	// vehicleSb.append(",");
	//
	// List<VehicleState> trajectory = entry.getValue();
	// for (int i = 0; i < trajectory.size(); i++) {
	//
	// frameSb.append("\"");
	// frameSb.append(initFrameId + i);
	// frameSb.append(",");
	// frameSb.append(name);
	// frameSb.append(",");
	// frameSb.append(trajectory.get(i).coord.x);
	// frameSb.append(",");
	// frameSb.append(trajectory.get(i).coord.y);
	// frameSb.append(",");
	// frameSb.append(trajectory.get(i).angle);
	// frameSb.append(",");
	// String color = Colors.getVehicleColor(trajectory.get(i).speed);
	// frameSb.append(color);
	// frameSb.append("\"");
	// frameSb.append(",");
	// }
	// }
	// if (vehicleSb.length() > 0)
	// vehicleSb.deleteCharAt(vehicleSb.length() - 1);
	// if (frameSb.length() > 0)
	// frameSb.deleteCharAt(frameSb.length() - 1);
	// return "{vehicles:[" + vehicleSb.toString() + "], elements:["
	// + frameSb.toString() + "]}";
	// }
}
