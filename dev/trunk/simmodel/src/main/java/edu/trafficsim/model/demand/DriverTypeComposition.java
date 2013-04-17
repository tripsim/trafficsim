package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.Composition;
import edu.trafficsim.model.roadusers.DriverType;

public interface DriverTypeComposition extends Composition<DriverType> {

	public Set<DriverType> getDriverTypes();

}
