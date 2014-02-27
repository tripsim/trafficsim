package edu.trafficsim.model;

import java.util.Set;

import edu.trafficsim.model.roadusers.VehicleType;


public interface VehicleTypeComposition extends Composition<VehicleType> {

	public Set<VehicleType> getVehicleTypes();

}
