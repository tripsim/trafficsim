package edu.trafficsim.model;

import java.util.Set;

import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public interface VehicleClassComposition extends Composition<VehicleClass> {

	public Set<VehicleClass> getVehicleClasses();

}
