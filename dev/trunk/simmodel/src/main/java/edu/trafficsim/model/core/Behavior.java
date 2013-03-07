package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.roadusers.Vehicle;

public interface Behavior extends DataContainer {

	public void update(Vehicle vehicle);
}