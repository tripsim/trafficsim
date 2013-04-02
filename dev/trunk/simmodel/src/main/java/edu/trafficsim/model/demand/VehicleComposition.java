package edu.trafficsim.model.demand;

import java.util.Collections;
import java.util.Set;

import edu.trafficsim.model.core.AbstractDynamicMap;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

abstract class VehicleComposition<T> extends AbstractDynamicMap<T, VehicleClassProportion> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VehicleComposition() {
	}
	
	double getVehicleClassProportion(T key, double time, VehicleClass vehicleClass) {
		VehicleClassProportion proportion = getProperty(key, time);
		return proportion == null ? 0 : proportion.get(vehicleClass);
	}
	
	VehicleClassProportion getVehicleClassProportion(T key, double time) {
		return getProperty(key, time);
	}
	
	void setVehicleClassProportion(T key, double time, VehicleClass vehicleClass, double value) {
		VehicleClassProportion proportion = getProperty(key, time);
		if (proportion == null) {
			proportion = new VehicleClassProportion();
			setProperty(key, time, proportion);
		}
		proportion.put(vehicleClass, value);
	}
	
	Set<VehicleClass> getVehicleClasses(T key, double time) {
		VehicleClassProportion proportion = getProperty(key, time);
		if (proportion == null)
			return Collections.emptySet();
		return proportion.keys();
	}
}
