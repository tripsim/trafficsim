package edu.trafficsim.model.network;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.Location;
import edu.trafficsim.model.roadusers.Vehicle;

public class Lane extends AbstractSegment<Lane> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static double DEFAULT_WIDTH = 4.0d;
	
	private Link link;
	
	private double width;
	// the distance in the link where the lane starts and ends
	private double start;
	private double end;
	
	private final NavigableSet<Vehicle> vehicles;
	private Map<Integer, Set<Vehicle>> laneFragements;
	
	public Lane(Link link) {
		this.link = link;
		
		this.vehicles = new TreeSet<Vehicle>();
		this.width = DEFAULT_WIDTH;
		this.start = 0;
		this.end = link.getLength();
	}
	
	public int getLaneId() {
		return link.getLanes().indexOf(this);
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getStart() {
		return start;
	}
	
	public void setStart(double start) {
		this.start = start;
	}
	
	public double getEnd() {
		return end;
	}
	
	public void setEnd(double end) {
		this.end = end;
	}
	
	public NavigableSet<Vehicle> getVehicles() {
		return vehicles;
	}
	
	public Vehicle getHeadVehicle() {
		return vehicles.first();
	}
	
	public Vehicle getTailVehicle() {
		return vehicles.last();
	}
	
	public Vehicle getLeadingVehicle(Vehicle v) {
		return vehicles.ceiling(v);
	}
	
	public Vehicle getPrecedingVehicle(Vehicle v) {
		return vehicles.floor(v);
	}
	
	public Set<Vehicle> getFragment(Integer i) {
		return laneFragements.get(i);
	}
	
	public Link getLink() {
		return link;
	}
	
	// TODO enforce minimum headway
	public void addVehicle(Vehicle vehicle) {
		vehicle.setLane(this);
		vehicle.setPosition(0);
		vehicles.add(vehicle);
	}

	@Override
	public Location getFromLocation() {
		return link.getFromLocation();
	}

	@Override
	public Location getToLocation() {
		return link.getToLocation();
	}

	@Override
	public String getName() {
		return link.getName() + "-" + getLaneId() + " "+ super.getName();
	}

	@Override
	public void setFromLocation(Location fromLocation) {
		return;
	}

	@Override
	public void setToLocation(Location toLocation) {
		return;
	}

}
