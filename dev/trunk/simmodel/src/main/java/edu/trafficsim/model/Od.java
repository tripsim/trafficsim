package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

public interface Od extends DataContainer {

	public Long getId();

	public Node getOrigin();

	public Node getDestination();

	public int vph(double time);

	public Collection<Double> getJumpTimes();

	public VehicleTypeComposition getVehicleTypeComposition(double time);

	public DriverTypeComposition getDriverTypeComposition(double time);

	void setVphs(double[] times, Integer[] vphs) throws ModelInputException;
}
