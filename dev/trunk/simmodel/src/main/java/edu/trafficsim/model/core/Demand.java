package edu.trafficsim.model.core;

import java.util.Set;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public interface Demand extends DataContainer {

	public Node getOriginNode();
	
	public Set<Node> getDestinationNodes();
	
	public Set<VehicleClass> getVehicleClasses();
	
	public int getVph(VehicleClass vehicleClass, double timestamp);

	public void setVph(VehicleClass vehicleClass, double timestamp, int vph);
	
	public int getVph(Node destination, VehicleClass vehicleClass, double timestamp);
	
	public double getRatio(Node destination, VehicleClass vehicleClass, double timestamp);
	
	public void setRatio(Node destination, VehicleClass vehicleClass, double timestamp, double ratio);
	
}
