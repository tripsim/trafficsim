package edu.trafficsim.model.roadusers;

import java.util.Set;
import java.util.TreeSet;

import edu.trafficsim.model.DataContainer;

public class VehicleQueues implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * NOT using at this time. Maybe, we will implement
	 * our own efficient collection to hold the vehicles
	 * within a lane or link.
	 */
	
	private Vehicle head;
	private Set<Vehicle> vehicles;
	
	public VehicleQueues() {
		vehicles = new TreeSet<Vehicle>();
	}

	public Vehicle getHead() {
		return head;
	}
	
	public Set<Vehicle> getVehicles() {
		return vehicles;
	}
}
