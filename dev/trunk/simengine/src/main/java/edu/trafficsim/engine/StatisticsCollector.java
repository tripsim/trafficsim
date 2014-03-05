package edu.trafficsim.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.utility.Timer;

public interface StatisticsCollector {

	public static final int INIT = 0;
	public static final int RUNNING = 1;
	public static final int DONE = 2;

	void begin(Timer timer);

	void finish();

	int status();

	boolean isDone();

	void stepForward(int forwardedSteps);

	void visit(Vehicle vehicle);

	void visit(Link link);

	void visit(Node node);

	List<StatisticsFrame> getFrames();

	Vehicle getVehicle(Long vid);

	Collection<Long> getVehicleIds();

	Collection<Long> getLinkIds();

	VehicleState[] trajectory(Long vid);

	Map<Long, List<VehicleState>> trajectories();

	LinkState[] linkStats(Long id);

	public static interface StatisticsFrame {

		double getTime();

		VehicleState getVehicleState(Long vid);

		Collection<Long> getVehicleIds();

		LinkState getLinkState(Long id);

		Collection<Long> getLinkIds();

	}

	public static class VehicleState {

		public final double time;
		public final Coordinate coord;
		public final double speed;
		public final double angle;

		public VehicleState(double time, Vehicle vehicle) {
			this.time = time;
			coord = vehicle.coord();
			speed = vehicle.speed();
			angle = vehicle.angle();
		}
	}

	public static class LinkState {

		public final double time;
		public final Map<Long, Double> speeds;
		public final Map<Long, Double> positions;

		public LinkState(double time) {
			this.time = time;
			speeds = new HashMap<Long, Double>();
			positions = new HashMap<Long, Double>();
		}

		public void update(Vehicle vehicle) {
			speeds.put(vehicle.getId(), vehicle.speed());
			positions.put(vehicle.getId(), vehicle.position());
		}
	}

}
