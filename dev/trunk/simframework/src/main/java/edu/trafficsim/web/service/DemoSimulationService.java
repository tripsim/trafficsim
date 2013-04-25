package edu.trafficsim.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.io.WKTWriter;

import edu.trafficsim.engine.demo.SimulationTest;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.ModelInputException;

@Service
public class DemoSimulationService {

	public String runSimulation() {
		try {
			List<Vehicle> vehicles = SimulationTest.getInstance().run();
			if (vehicles != null && vehicles.size() > 0) {
				StringBuffer vehicleSb = new StringBuffer();
				StringBuffer frameSb = new StringBuffer();

				for (Vehicle vehicle : vehicles) {

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

					Coordinate[] trajectory = vehicle.trajectory();
					for (int i = 0; i < trajectory.length; i++) {

						frameSb.append("\"");
						frameSb.append(initFrameId + i);
						frameSb.append(",");
						frameSb.append(name);
						frameSb.append(",");
						frameSb.append(trajectory[i].x);
						frameSb.append(",");
						frameSb.append(trajectory[i].y);
						frameSb.append(",");
						double angle = i == 0 ? 0 : Angle.toDegrees(Angle
								.angle(trajectory[i], trajectory[i - 1]));
						frameSb.append(angle);
						frameSb.append(",");
						frameSb.append("red");
						frameSb.append("\"");
						frameSb.append(",");
					}
				}
				vehicleSb.deleteCharAt(vehicleSb.length() - 1);
				frameSb.deleteCharAt(frameSb.length() - 1);
				return "{vehicles:[" + vehicleSb.toString() + "], elements:["
						+ frameSb.toString() + "]}";
			}
		} catch (ModelInputException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public String getNetwork() {
		Network network = SimulationTest.getInstance().getNetwork();
		WKTWriter writer = new WKTWriter();
		StringBuffer linkSb = new StringBuffer();
		for (Link link : network.getLinks()) {
			linkSb.append("\"");
			linkSb.append(writer.write(link.getLinearGeom()));
			linkSb.append("\"");
			linkSb.append(",");
		}
		linkSb.deleteCharAt(linkSb.length() - 1);
		return "{links:[" + linkSb.toString() + "]}";
	}
}
