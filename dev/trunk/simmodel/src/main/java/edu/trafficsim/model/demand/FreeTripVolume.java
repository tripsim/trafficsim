package edu.trafficsim.model.demand;

import java.util.Collections;
import java.util.Set;

import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class FreeTripVolume extends AbstractTripVolume<FreeTripVolume> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public FreeTripVolume(Node origin) {
		super(origin);
	}

	@Override
	public Set<Node> getDestinationNodes() {
		return Collections.emptySet();
	}

	@Override
	public final double getRatio(Node destination, VehicleClass vehicleClass, double timestamp) {
		return 0;
	}

	@Override
	public final void setRatio(Node destination, VehicleClass vehicleClass, double timestamp, double ratio) {
	}

}
