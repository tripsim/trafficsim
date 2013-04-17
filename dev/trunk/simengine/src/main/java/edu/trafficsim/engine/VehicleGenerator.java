package edu.trafficsim.engine;

import java.util.List;

import edu.trafficsim.engine.generator.VehicleToAdd;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.Simulator;

public interface VehicleGenerator {

	public List<VehicleToAdd> getVehicleToAdd(Od od, Simulator simulator);

}
