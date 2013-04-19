package edu.trafficsim.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.engine.demo.SimulationTest;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.ModelInputException;

@Service
public class DemoSimulationService {

	public String runSimulation() {
		try {
			List<Vehicle> vehicles = SimulationTest.run();
			if (vehicles != null && vehicles.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (Vehicle vehicle : vehicles) {
					String name = vehicle.getName();
					String type = vehicle.getVehicleType().getName();
					int initFrameId = vehicle.getStartFrame();
					Coordinate[] trajectory = vehicle.trajectory();
					// TODO use JSON
					for (int i = 0; i < trajectory.length; i++) {
						sb.append(name);
						sb.append(",");
						sb.append(trajectory[i].x);
						sb.append(",");
						sb.append(trajectory[i].y);
						sb.append(",");
						sb.append(type);
						sb.append(",");
						sb.append(initFrameId + i);
						sb.append("\n");
					}
				}
				return sb.toString();
			}
		} catch (ModelInputException e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
