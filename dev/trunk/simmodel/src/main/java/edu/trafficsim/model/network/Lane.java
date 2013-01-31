package edu.trafficsim.model.network;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import edu.trafficsim.model.core.Location;
import edu.trafficsim.model.core.Segment;
import edu.trafficsim.model.roadusers.Vehicle;

public class Lane implements Segment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Link link;
//	private double start;
//	private double end;
//	private double portion; // how much portion of width it occupies comparing to even distribution
	private short laneNumber;
	private Vehicle head;
	private Vehicle tail;
	private NavigableSet<Vehicle> vehicles;
	private Map<Integer, Set<Vehicle>> neighborhoods;
	
	public Lane(Link link, short laneNumber) {
		this.link = link;
		this.laneNumber = laneNumber;
		this.vehicles = new TreeSet<Vehicle>();
//		this.start = 0;
//		this.end = link.getLength();
//		this.portion = 1.0;
	}
	
	public short getLaneNumber() {
		return laneNumber;
	}
	
	public NavigableSet<Vehicle> getVehicles() {
		return vehicles;
	}
	
	public Vehicle getHeadVehicle() {
		return head;
	}
	
	public Vehicle getTailVehicle() {
		return tail;
	}
	
	public Vehicle getLeadingVehicle(Vehicle v) {
		return vehicles.higher(v);
	}
	
	public Vehicle getPrecedingVehicle(Vehicle v) {
		return vehicles.lower(v);
	}
	
	public Set<Vehicle> getNeighborhood(Integer i) {
		return neighborhoods.get(i);
	}
	
	public Link getLink() {
		return link;
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
		return link.getName() + laneNumber;
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
