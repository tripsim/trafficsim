package edu.trafficsim.model;

import java.util.Random;

import edu.trafficsim.model.VehicleType.VehicleClass;

public interface Router extends DataContainer {

	public Link getSucceedingLink(Link precedingLink,
			VehicleClass vehicleClass, double forwardedTime, Random rand);

}
