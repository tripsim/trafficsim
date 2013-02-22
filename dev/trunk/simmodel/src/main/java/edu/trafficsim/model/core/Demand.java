package edu.trafficsim.model.core;

import java.util.Set;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleClass;

public interface Demand extends DataContainer {

	public Node getOriginNode();
	
	public Set<Node> getDestinationNodes();
	
	public int getDemand(VehicleClass vehicleClass);
	
	public int getDemandByDestination(Node destination, VehicleClass vehicleClass);
}
