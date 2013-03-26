package edu.trafficsim.model.core;

import java.util.Set;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public interface Origin extends DataContainer {

	public Node getNode();
	
	public Set<Destination> getDestinations();
	
	public Set<VehicleClass> getVehicleClasses();
	
	public int getVph(VehicleClass vehicleClass, double timestamp);

	public void setVph(VehicleClass vehicleClass, double timestamp, int vph);
	
	public int getVph(Destination destination, VehicleClass vehicleClass, double timestamp);
	
	public double getProportion(Destination destination, VehicleClass vehicleClass, double timestamp);
	
	public void setProportion(Destination destination, VehicleClass vehicleClass, double timestamp, double ratio);
	
}
