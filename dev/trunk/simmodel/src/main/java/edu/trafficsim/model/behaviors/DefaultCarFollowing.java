package edu.trafficsim.model.behaviors;

import edu.trafficsim.model.CarFollowingBehavior;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.roadusers.DefaultVehicle;

public class DefaultCarFollowing extends AbstractBehavior<DefaultCarFollowing>
		implements CarFollowingBehavior {

	private static final long serialVersionUID = 1L;

	public DefaultCarFollowing() {
	}

	public DefaultCarFollowing(String name) {
		setName(name);
	}

	// HACK
	public static final double alpha = 0.2;

	@Override
	public void update(Vehicle vehicle) {
		Vehicle leading = vehicle.getPrecedingVehicle();
		double accerl = 0;
		if (leading != null)
			accerl = alpha * (leading.speed() - vehicle.speed());
		// HACK
		((DefaultVehicle) vehicle).acceleration(accerl);
	}

}