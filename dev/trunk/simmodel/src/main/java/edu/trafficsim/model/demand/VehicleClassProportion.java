package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.core.AbstractProportion;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

class VehicleClassProportion extends AbstractProportion<VehicleClass> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	VehicleClassProportion() {}

	Set<VehicleClass> getVehicleClasses() {
		return keys();
	}
}
