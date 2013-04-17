package edu.trafficsim.plugin;

import edu.trafficsim.model.Vehicle;

public interface ICarFollowing extends IPlugin {

	public void update(Vehicle vehicle);

}