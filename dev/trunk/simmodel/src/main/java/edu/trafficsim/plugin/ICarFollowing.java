package edu.trafficsim.plugin;

import edu.trafficsim.model.roadusers.Vehicle;

public interface ICarFollowing extends IPlugin {

	public void update(Vehicle vehicle);

}