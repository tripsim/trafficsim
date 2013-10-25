package edu.trafficsim.engine;

import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;

public interface SimulationScenario {

	public OdMatrix getOdMatrix();

	public Router getRouter();

	public VehicleFactory getVehicleFactory();

	public VehicleGenerator getVehicleGenerator();
}
