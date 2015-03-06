package edu.trafficsim.api.model;

import java.io.Serializable;
import java.util.Collection;

public interface VehicleQueue extends Serializable {

	Vehicle getHeadVehicle();

	Vehicle getTailVehicle();

	Vehicle getLeadingVehicle(Vehicle vehicle);

	Vehicle getPrecedingVehicle(Vehicle vehicle);

	void addImediately(Vehicle vehicle);

	void add(Vehicle vehicle);

	void flush();

	void remove(Vehicle vehicle);

	Collection<Vehicle> getVehicles();

	boolean contains(Vehicle vehicle);

	boolean isEmpty();
}
