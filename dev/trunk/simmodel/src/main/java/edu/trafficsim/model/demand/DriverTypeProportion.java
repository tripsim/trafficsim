package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.core.AbstractProportion;
import edu.trafficsim.model.roadusers.DriverType;

class DriverTypeProportion extends AbstractProportion<DriverType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DriverTypeProportion() { }

	Set<DriverType> getDriverTypes() {
		return keys();
	}
}
