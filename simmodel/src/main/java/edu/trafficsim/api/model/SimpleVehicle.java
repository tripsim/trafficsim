package edu.trafficsim.api.model;

import edu.trafficsim.api.Agent;

public interface SimpleVehicle extends Movable, Agent {

	String getVehicleType();

	String getDriverType();

	VehicleClass getVehicleClass();
}
