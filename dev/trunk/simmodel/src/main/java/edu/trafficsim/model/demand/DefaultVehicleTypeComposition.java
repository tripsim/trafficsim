package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.AbstractComposition;

public class DefaultVehicleTypeComposition extends
		AbstractComposition<VehicleType> implements VehicleTypeComposition {

	private static final long serialVersionUID = 1L;

	public DefaultVehicleTypeComposition(long id, String name,
			VehicleType[] vehicleTypes, Double[] probabilities) {
		super(id, name, vehicleTypes, probabilities);
	}

	@Override
	public final Set<VehicleType> getVehicleTypes() {
		return keys();
	}

}
