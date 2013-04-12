package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.core.AbstractProportion;
import edu.trafficsim.model.roadusers.VehicleType;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

class VehicleTypeProportion extends AbstractProportion<VehicleType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final VehicleClass vehicleClass;

	VehicleTypeProportion(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	final Set<VehicleType> getVehicleTypes() {
		return keys();
	}

	final VehicleClass getVehicleClass() {
		return vehicleClass;
	}
}
