package edu.trafficsim.model.network;

import java.util.NavigableSet;
import java.util.TreeSet;

import edu.trafficsim.model.core.AbstractSegmentElement;
import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.roadusers.Vehicle;

public class Lane extends AbstractSegmentElement<Lane> implements Path {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int laneId;

	private final NavigableSet<Vehicle> vehicles = new TreeSet<Vehicle>();

	public Lane(int laneId, Link link, double width, double shift)
			throws ModelInputException {
		this(laneId, link, 0, 1, width, shift);
	}

	public Lane(int laneId, Link link, double start, double end, double width,
			double shift) throws ModelInputException {
		super(link, start, end, width, shift);
		this.laneId = laneId;
	}

	public Lane(Connector connector, double width) throws ModelInputException {
		super(connector, 0, 1, width, 0);
		this.laneId = -1;
	}

	@Override
	public final Link getNavigable() {
		return (Link) segment;
	}

	public final int getLaneId() {
		return laneId;
	}

	@Override
	public String getName() {
		return ((BaseEntity<?>) segment).getName() + "-" + getLaneId() + " "
				+ super.getName();
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
		vehicle.setPath(this);
		vehicles.add(vehicle);
	}

	@Override
	public final void remove(Vehicle vehicle) {
		vehicles.remove(vehicle);
		vehicle.setPath(null);
	}

	@Override
	public final void refresh(Vehicle vehicle) {
		vehicles.remove(vehicle);
		vehicles.add(vehicle);
	}
	
	@Override
	public final Vehicle[] getVehicles() {
		return vehicles.toArray(new Vehicle[0]);
	}

}
