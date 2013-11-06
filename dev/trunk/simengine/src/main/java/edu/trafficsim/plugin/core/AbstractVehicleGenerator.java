package edu.trafficsim.plugin.core;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.plugin.IVehicleGenerator;

public abstract class AbstractVehicleGenerator implements IVehicleGenerator {

	protected VehicleFactory vehicleFactory;

	AbstractVehicleGenerator(VehicleFactory vehicleFactory) {
		this.vehicleFactory = vehicleFactory;
	}

}
