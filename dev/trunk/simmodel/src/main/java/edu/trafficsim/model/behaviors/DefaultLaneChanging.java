package edu.trafficsim.model.behaviors;

import edu.trafficsim.model.LaneChangingBehavior;
import edu.trafficsim.model.Vehicle;

public class DefaultLaneChanging extends AbstractBehavior<DefaultLaneChanging>
		implements LaneChangingBehavior {

	private static final long serialVersionUID = 1L;

	public DefaultLaneChanging() {
	}

	public DefaultLaneChanging(String name) {
		setName(name);

	}

	@Override
	public void update(Vehicle vehicle) {

	}
}