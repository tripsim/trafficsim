package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

public interface Od extends DataContainer {

	Long getId();

	Node getOrigin();

	Node getDestination();

	Node setDestination(Node destination);

	int vph(double time);

	Collection<Double> getJumpTimes();

	Collection<Integer> getVphs();

	void setVphs(double[] times, Integer[] vphs) throws ModelInputException;

	VehicleTypeComposition getVehicleComposition();

	void setVehicleComposition(VehicleTypeComposition composition);

	DriverTypeComposition getDriverComposition();

	void setDriverComposition(DriverTypeComposition composition);

}
