package edu.trafficsim.engine.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;

public class DefaultStatisticsCollector implements StatisticsCollector {

	private final List<StatisticsFrame> frames;
	private final Simulator simulator;
	private DefaultStatisticsFrame currentFrame;

	public static class DefaultStatisticsFrame implements StatisticsFrame {
		int id;

		Map<Vehicle, VehicleState> vehicleStates;

		public DefaultStatisticsFrame(int id) {
			this.id = id;
			vehicleStates = new HashMap<Vehicle, VehicleState>();
		}

		@Override
		public int getFrameId() {
			return id;
		}

		@Override
		public VehicleState getVehicleState(Vehicle vehicle) {
			return vehicleStates.get(vehicle);
		}

		@Override
		public Collection<Vehicle> getVehicles() {
			return vehicleStates.keySet();
		}

	}

	public static StatisticsCollector create(Simulator simulator) {
		return new DefaultStatisticsCollector(simulator);
	}

	protected DefaultStatisticsCollector(Simulator simulator) {
		this.simulator = simulator;
		frames = new ArrayList<StatisticsFrame>(simulator.getTotalSteps());
		stepForward();
	}

	@Override
	public void stepForward() {
		currentFrame = new DefaultStatisticsFrame(simulator.getForwardedSteps());
		frames.add(currentFrame);
	}

	@Override
	public void visit(Vehicle vehicle) {
		currentFrame.vehicleStates.put(vehicle, new VehicleState(vehicle));
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
	public List<StatisticsFrame> getFrames() {
		return Collections.unmodifiableList(frames);
	}

	@Override
	public VehicleState[] trajectory(Vehicle vehicle) {
		List<VehicleState> trajectory = new ArrayList<VehicleState>();
		for (StatisticsFrame frame : frames) {
			VehicleState state = frame.getVehicleState(vehicle);
			if (state == null)
				continue;
			trajectory.add(state);
		}
		return trajectory.toArray(new VehicleState[0]);
	}

	@Override
	public Map<Vehicle, List<VehicleState>> trajectories() {
		Map<Vehicle, List<VehicleState>> trajectories = new HashMap<Vehicle, List<VehicleState>>();
		for (StatisticsFrame frame : frames) {
			for (Vehicle v : frame.getVehicles()) {
				List<VehicleState> trajectory = trajectories.get(v);
				if (trajectory == null)
					trajectories.put(v,
							trajectory = new ArrayList<VehicleState>());
				trajectory.add(frame.getVehicleState(v));
			}
		}
		return trajectories;
	}

}
