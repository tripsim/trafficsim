package edu.trafficsim.engine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Vehicle;

public interface StatisticsCollector {

	public void stepForward();

	public void visit(Vehicle vehicle);

	public void visit(Link link);

	public void visit(Node node);

	public List<StatisticsFrame> getFrames();

	public VehicleState[] trajectory(Vehicle vehicle);

	public Map<Vehicle, List<VehicleState>> trajectories();

	public static interface StatisticsFrame {

		public int getFrameId();

		public VehicleState getVehicleState(Vehicle vehicle);

		public Collection<Vehicle> getVehicles();

	}

	public static class VehicleState {

		public final Coordinate coord;
		public final double speed;
		public final double angle;

		public VehicleState(Vehicle vehicle) {
			coord = vehicle.coord();
			speed = vehicle.speed();
			angle = vehicle.angle();
		}
	}

}
