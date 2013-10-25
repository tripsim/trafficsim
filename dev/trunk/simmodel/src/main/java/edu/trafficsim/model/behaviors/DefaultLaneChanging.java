package edu.trafficsim.model.behaviors;

import edu.trafficsim.model.LaneChangingBehavior;
import edu.trafficsim.model.Vehicle;

public class DefaultLaneChanging extends AbstractBehavior<DefaultLaneChanging>
		implements LaneChangingBehavior {

	private static final long serialVersionUID = 1L;

	public DefaultLaneChanging(long id, String name) {
		super(id, name);
	}

	@Override
	public void update(Vehicle vehicle) {

	}
}