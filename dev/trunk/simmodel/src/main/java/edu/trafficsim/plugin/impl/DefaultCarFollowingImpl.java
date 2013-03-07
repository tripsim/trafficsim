package edu.trafficsim.plugin.impl;

import edu.trafficsim.model.roadusers.Vehicle;
import edu.trafficsim.plugin.ICarFollowing;

public class DefaultCarFollowingImpl extends AbstractPluginImpl implements ICarFollowing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void update(Vehicle vehicle) {
		vehicle.setPosition(vehicle.getPosition() + 1.0);
	}

}