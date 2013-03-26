package edu.trafficsim.model.demand;

import java.util.Collections;
import java.util.Set;

import edu.trafficsim.model.core.Destination;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class FreeTripGenerator extends AbstractTripGenerator<FreeTripGenerator> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public FreeTripGenerator(Node origin) {
		super(origin);
	}

	@Override
	public Set<Destination> getDestinations() {
		return Collections.emptySet();
	}

	@Override
	public final double getProportion(Destination destination, VehicleClass vehicleClass, double timestamp) {
		return 0;
	}

	@Override
	public final void setProportion(Destination destination, VehicleClass vehicleClass, double timestamp, double ratio) {
	}

}
