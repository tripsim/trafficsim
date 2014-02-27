package edu.trafficsim.model;

import java.util.Random;

import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public interface Router extends DataContainer {

	public Link getSucceedingLink(Link precedingLink,
			VehicleClass vehicleClass, double forwardedTime, Random rand);

}
