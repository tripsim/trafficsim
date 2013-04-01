package edu.trafficsim.model.core;

import java.util.Set;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public interface Origin extends DataContainer {

	public Node getNode();
	
	public Set<Destination> getDestinations();

	public int getVph(double time);

	public int getVph(Destination destination, double time);
	
	public void setVph(Destination destination, double time, int vph) ;
	
	public double getVehicleClassRate(Destination destination, double time, VehicleClass vehicleClass);
	
	public void setVehicleClassProportion(Destination destination, double time, VehicleClass vehicleClass, double value);
	
}
