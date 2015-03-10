package org.tripsim.api.model;

import org.tripsim.api.Agent;

public interface SimpleVehicle extends Movable, Agent {

	String getVehicleType();

	String getDriverType();

	VehicleClass getVehicleClass();
}
