package edu.trafficsim.web.service;

import java.util.List;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.StatisticsCollector.VehicleState;
import edu.trafficsim.engine.demo.DemoSimulation;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.Colors;
import edu.trafficsim.model.core.ModelInputException;

@Service
public class DemoSimulationService {

	public String runSimulation() throws TransformException {
		try {
			StatisticsCollector statisticsCollector = DemoSimulation
					.getInstance().run();
			StringBuffer vehicleSb = new StringBuffer();
			StringBuffer frameSb = new StringBuffer();

			for (Map.Entry<Vehicle, List<VehicleState>> entry : statisticsCollector
					.trajectories().entrySet()) {

				Vehicle vehicle = entry.getKey();

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
					String color = Colors
							.getVehicleColor(trajectory.get(i).speed);
					frameSb.append(color);
					frameSb.append("\"");
					frameSb.append(",");
				}
			}
			vehicleSb.deleteCharAt(vehicleSb.length() - 1);
			frameSb.deleteCharAt(frameSb.length() - 1);
			return "{vehicles:[" + vehicleSb.toString() + "], elements:["
					+ frameSb.toString() + "]}";
		} catch (ModelInputException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public SimulationScenario getScenario() throws TransformException {
		return DemoSimulation.getInstance().getScenario();
	}

}
