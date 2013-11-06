package edu.trafficsim.plugin;

import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;

public interface IMoving extends IPlugin {

	public void update(Vehicle vehicle, Simulator simulator);
}
