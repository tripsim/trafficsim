package edu.trafficsim.model;

import java.util.Set;

import edu.trafficsim.model.roadusers.DriverType;


public interface DriverTypeComposition extends Composition<DriverType> {

	public Set<DriverType> getDriverTypes();

}
