package edu.trafficsim.model.roadusers;

import java.util.Set;
import java.util.TreeSet;

import edu.trafficsim.model.DataContainer;

public class VehicleQueues implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vehicle headVehicle;
	private Set<Vehicle> vehicles;
	
	public VehicleQueues() {
		vehicles = new TreeSet<Vehicle>();
	}

	public Vehicle getHeadVehicle() {
		return headVehicle;
	}
	
	public Set<Vehicle> getVehicles() {
		return vehicles;
	}
}
