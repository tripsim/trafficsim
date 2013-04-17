package edu.trafficsim.model.behaviors;

import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.ILaneChanging;


public class LaneChangingBehavior extends AbstractBehavior<LaneChangingBehavior> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_IMPL_NAME = "Default";
	
	private ILaneChanging impl;
	
	public LaneChangingBehavior() {
		this(DEFAULT_IMPL_NAME);
	}
	
	public LaneChangingBehavior(String name) {
		setName(name);
		
	}

	@Override
	public void update(Vehicle vehicle) {
		
	}
}