package edu.trafficsim.plugin.core;

import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.ICarFollowing;

public class DefaultCarFollowing extends AbstractPlugin implements
		ICarFollowing {

	private static final long serialVersionUID = 1L;

	@Override
	public void update(Vehicle vehicle, SimulationScenario simulationScenario) {
		// TODO make sure it doesn't collide leading vehicle

	}

}
