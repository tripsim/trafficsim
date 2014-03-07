package edu.trafficsim.model;

import java.util.Set;

public interface VehicleTypeComposition extends Composition<VehicleType> {

	Set<VehicleType> getTypes();

}
