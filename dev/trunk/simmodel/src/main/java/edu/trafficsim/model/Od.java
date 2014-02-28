package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

public interface Od extends DataContainer {

	Long getId();

	Node getOrigin();

	Node getDestination();

	Node setDestination(Node destination);

	Collection<Double> getJumpTimes();

	int vph(double time);

	void setVphs(double[] times, Integer[] vphs) throws ModelInputException;

	VehicleTypeComposition getVehicleComposition(double time);

	DriverTypeComposition getDriverComposition(double time);

	Collection<VehicleTypeComposition> getVehicleCompositions();

	Collection<DriverTypeComposition> getDriverCompositions();

	void setVehicleComposiion(VehicleTypeComposition vehicleComposition);

	void setDriverComposition(DriverTypeComposition driverComposition);
}
