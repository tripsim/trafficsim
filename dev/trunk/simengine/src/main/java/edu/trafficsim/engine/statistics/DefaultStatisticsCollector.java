package edu.trafficsim.engine.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.utility.Timer;

public class DefaultStatisticsCollector implements StatisticsCollector {

	private final List<StatisticsFrame> frames;

	private final Map<Long, Vehicle> vehicles;
	private final Set<Long> links;

	private int status;
	private double stepSize = 0;
	private DefaultStatisticsFrame currentFrame;

	public static class DefaultStatisticsFrame implements StatisticsFrame {
		double time;

		Map<Long, VehicleState> vehicleStates;
		Map<Long, LinkState> linkStates;

		public DefaultStatisticsFrame(double time) {
			this.time = time;
			vehicleStates = new HashMap<Long, VehicleState>();
			linkStates = new HashMap<Long, LinkState>();
		}

		@Override
		public double getTime() {
			return time;
		}

		@Override
		public VehicleState getVehicleState(Long vid) {
			return vehicleStates.get(vid);
		}

		@Override
		public Collection<Long> getVehicleIds() {
			return vehicleStates.keySet();
		}

		@Override
		public LinkState getLinkState(Long id) {
			return linkStates.get(id);
		}

		@Override
		public Collection<Long> getLinkIds() {
			return linkStates.keySet();
		}

	}

	public static StatisticsCollector create() {
		StatisticsCollector statistics = new DefaultStatisticsCollector();
		return statistics;
	}

	protected DefaultStatisticsCollector() {
		frames = new ArrayList<StatisticsFrame>();
		vehicles = new HashMap<Long, Vehicle>();
		links = new HashSet<Long>();
		status = StatisticsCollector.INIT;
	}

	@Override
	public void begin(Timer timer) {
		frames.clear();
		vehicles.clear();
		links.clear();
		stepSize = timer.getStepSize();
		stepForward(0);
		status = StatisticsCollector.RUNNING;
	}

	@Override
	public void finish() {
		status = StatisticsCollector.DONE;
	}

	@Override
	public int status() {
		return status;
	}

	@Override
	public boolean isDone() {
		return status == StatisticsCollector.DONE;
	}

	@Override
	public void stepForward(int forwardedSteps) {
		currentFrame = new DefaultStatisticsFrame(forwardedSteps * stepSize);
		frames.add(currentFrame);
	}

	@Override
	public void visit(Vehicle vehicle) {
		if (vehicle.position() <= 0)
			return;

		if (!vehicles.containsKey(vehicle.getId()))
			vehicles.put(vehicle.getId(), vehicle);
		currentFrame.vehicleStates.put(vehicle.getId(), new VehicleState(
				currentFrame.time, vehicle));

		LinkState linkState = currentFrame.linkStates.get(vehicle.getLink()
				.getId());
		if (linkState == null) {
			currentFrame.linkStates.put(vehicle.getLink().getId(),
					linkState = new LinkState(currentFrame.time));
			links.add(vehicle.getLink().getId());
		}
		if (!vehicle.onConnector())
			linkState.update(vehicle);
	}

	@Override
	public void visit(Link link) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Node node) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vehicle getVehicle(Long vid) {
		return vehicles.get(vid);
	}

	@Override
	public Collection<Long> getVehicleIds() {
		return vehicles.keySet();
	}

	@Override
	public Collection<Long> getLinkIds() {
		return links;
	}

	@Override
	public List<StatisticsFrame> getFrames() {
		return Collections.unmodifiableList(frames);
	}

	@Override
	public VehicleState[] trajectory(Long vid) {
		List<VehicleState> trajectory = new ArrayList<VehicleState>();
		for (StatisticsFrame frame : frames) {
			VehicleState state = frame.getVehicleState(vid);
			if (state == null)
				continue;
			trajectory.add(state);
		}
		return trajectory.toArray(new VehicleState[0]);
	}

	@Override
	public LinkState[] linkStats(Long id) {
		List<LinkState> linkStats = new ArrayList<LinkState>();
		for (StatisticsFrame frame : frames) {
			LinkState state = frame.getLinkState(id);
			if (state == null)
				continue;
			linkStats.add(state);
		}
		return linkStats.toArray(new LinkState[0]);
	}

	@Override
	public Map<Long, List<VehicleState>> trajectories() {
		Map<Long, List<VehicleState>> trajectories = new HashMap<Long, List<VehicleState>>();
		for (StatisticsFrame frame : frames) {
			for (Long vid : frame.getVehicleIds()) {
				List<VehicleState> trajectory = trajectories.get(vid);
				if (trajectory == null)
					trajectories.put(vid,
							trajectory = new ArrayList<VehicleState>());
				trajectory.add(frame.getVehicleState(vid));
			}
		}
		return trajectories;
	}

}
