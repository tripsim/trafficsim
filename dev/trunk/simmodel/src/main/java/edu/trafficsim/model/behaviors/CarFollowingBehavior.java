package edu.trafficsim.model.behaviors;

import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.ICarFollowing;
import edu.trafficsim.plugin.impl.DefaultCarFollowingImpl;


public class CarFollowingBehavior extends AbstractBehavior<CarFollowingBehavior> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_IMPL_NAME = "Default";
	
	private ICarFollowing impl;
	
	public CarFollowingBehavior() {
		this(DEFAULT_IMPL_NAME);
	}
	
	public CarFollowingBehavior(String name) {
		setName(name);
		// Get the proper impl....
		
		// HACK
		impl = new DefaultCarFollowingImpl();
	}
	
	@Override
	public void update(Vehicle vehicle) {
		impl.update(vehicle);
	}

}