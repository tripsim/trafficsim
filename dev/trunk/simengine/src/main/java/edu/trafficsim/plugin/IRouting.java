package edu.trafficsim.plugin;

import java.util.Random;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleType.VehicleClass;

public interface IRouting extends IPlugin {

	Link getSucceedingLink(OdMatrix odMatrix, Link precedingLink,
			VehicleClass vehicleClass, double forwardedTime, Random rand);

	Link getSucceedingLink(Link precedingLink, VehicleClass vehicleClass,
			double forwardedTime, Random rand);

}
