package edu.trafficsim.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.io.WKTWriter;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.StatisticsCollector.LinkState;
import edu.trafficsim.engine.StatisticsCollector.VehicleState;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.MultiValuedMap;
import edu.trafficsim.model.util.Colors;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.SimulationResult;

@Service
public class StatisticsService {

	@Autowired
	SimulationProject project;
	@Autowired
	SimulationResult result;

	private static WKTWriter writer = new WKTWriter();

	public String getTrajectory(Long vid) {
		StatisticsCollector statics = result.getStatistics();
		VehicleState[] vehicleStates = statics.trajectory(vid);
		Coordinate[] coords = new Coordinate[vehicleStates.length];
		for (int i = 0; i < vehicleStates.length; i++) {
			coords[i] = vehicleStates[i].coord;
		}
		return writer.write(project.getNetworkFactory()
				.createLineString(coords));
	}

	public String getTsdPlotData(Long id) {
		StatisticsCollector statics = result.getStatistics();
		LinkState[] linkStats = statics.linkStats(id);
		MultiValuedMap<Long, String> data = new MultiValuedMap<Long, String>();
		for (int i = 0; i < linkStats.length; i++) {
			for (Map.Entry<Long, Double> entry : linkStats[i].positions
					.entrySet()) {
				data.add(entry.getKey(),
						"[" + linkStats[i].time + "," + entry.getValue() + "]");
			}
		}
		StringBuffer sb = new StringBuffer();
		for (Long vid : data.keys()) {
			sb.append(data.get(vid));
			sb.append(",");
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return "[" + sb.toString() + "]";
	}

	public String getFrames() {
		StatisticsCollector statics = result.getStatistics();

		StringBuffer vehicleSb = new StringBuffer();
		StringBuffer frameSb = new StringBuffer();

		for (Map.Entry<Long, List<VehicleState>> entry : statics.trajectories()
				.entrySet()) {

			Vehicle vehicle = statics.getVehicle(entry.getKey());

			String name = vehicle.getName();
			int initFrameId = vehicle.getStartFrame();

			vehicleSb.append("\"");
			vehicleSb.append(name);
			vehicleSb.append(",");
			vehicleSb.append(vehicle.getWidth());
			vehicleSb.append(",");
			vehicleSb.append(vehicle.getLength());
			vehicleSb.append("\"");
			vehicleSb.append(",");

			List<VehicleState> trajectory = entry.getValue();
			for (int i = 0; i < trajectory.size(); i++) {

				frameSb.append("\"");
				frameSb.append(initFrameId + i);
				frameSb.append(",");
				frameSb.append(name);
				frameSb.append(",");
				frameSb.append(trajectory.get(i).coord.x);
				frameSb.append(",");
				frameSb.append(trajectory.get(i).coord.y);
				frameSb.append(",");
				frameSb.append(trajectory.get(i).angle);
				frameSb.append(",");
				String color = Colors.getVehicleColor(trajectory.get(i).speed);
				frameSb.append(color);
				frameSb.append("\"");
				frameSb.append(",");
			}
		}
		if (vehicleSb.length() > 0)
			vehicleSb.deleteCharAt(vehicleSb.length() - 1);
		if (frameSb.length() > 0)
			frameSb.deleteCharAt(frameSb.length() - 1);
		return "{vehicles:[" + vehicleSb.toString() + "], elements:["
				+ frameSb.toString() + "]}";
	}
}
