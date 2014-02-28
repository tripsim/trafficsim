package edu.trafficsim.plugin;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;

public interface IMoving extends IPlugin {

	void update(Vehicle vehicle, SimulationScenario simulationScenario)
			throws TransformException;
}
