package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.AbstractSubsegment;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultLane extends AbstractSubsegment<DefaultLane> implements
		Lane {

	private static final long serialVersionUID = 1L;

	private final int laneId;
	private final NavigableSet<Vehicle> vehicles = new TreeSet<Vehicle>();

	public DefaultLane(long id, Segment segment, double width, double shift,
			int laneId) {
		super(id, null, segment, width, shift);
		this.laneId = laneId;
	}

	public DefaultLane(long id, Segment segment, double start, double end,
			double width, double shift, int laneId) throws ModelInputException {
		super(id, null, segment, start, end, width, shift);
		this.laneId = laneId;
	}

	@Override
	public final String getName() {
		return segment.getName() + " " + laneId;
	}

	@Override
	public final Vehicle getHeadVehicle() {
		return vehicles.last();
	}

	@Override
	public final Vehicle getTailVehicle() {
		return vehicles.first();
	}

	@Override
	public final Vehicle getLeadingVehicle(Vehicle v) {
		return vehicles.ceiling(v);
	}

	@Override
	public final Vehicle getPrecedingVehicle(Vehicle v) {
		return vehicles.floor(v);
	}

	@Override
	public final void add(Vehicle vehicle) {
		if (vehicle.currentLane() != null)
			vehicle.currentLane().remove(vehicle);
		vehicles.add(vehicle);
	}

	@Override
	public final void remove(Vehicle vehicle) {
		vehicles.remove(vehicle);
	}

	@Override
	public final void update(Vehicle vehicle) {
		vehicles.remove(vehicle);
		vehicles.add(vehicle);
	}

	@Override
	public final Collection<Vehicle> getVehicles() {
		return Collections.unmodifiableCollection(vehicles);
	}

	@Override
	public int getLaneId() {
		return laneId;
	}

	@Override
	public int compareTo(DefaultLane lane) {
		if (!segment.equals(lane.getSegment()))
			return super.compareTo(lane);
		return laneId > lane.laneId ? 1 : (laneId > lane.laneId ? -1 : 0);
	}

}
