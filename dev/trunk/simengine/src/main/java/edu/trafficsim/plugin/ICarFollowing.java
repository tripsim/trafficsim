package edu.trafficsim.plugin;

import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;

public interface ICarFollowing extends IPlugin {

	void update(Vehicle vehicle, SimulationScenario simulationScenario);
}
