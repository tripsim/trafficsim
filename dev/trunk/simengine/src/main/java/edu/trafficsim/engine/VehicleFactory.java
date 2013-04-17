package edu.trafficsim.engine;

import edu.trafficsim.engine.generator.VehicleToAdd;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;

public interface VehicleFactory {

	Vehicle createVehicle(VehicleToAdd vehicleToAdd, Simulator simulator);

}
