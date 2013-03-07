 package edu.trafficsim.model.demand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.Demand;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public abstract class AbstractTripGenerator<T> extends BaseEntity<T> implements Demand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Node origin;
	private Map<VehicleClass, Integer> demands;
	private Map<Node, Map<VehicleClass, Integer>> destinationDemands;
	
	public AbstractTripGenerator(Node origin) {
		this.origin = origin;
		demands = new HashMap<VehicleClass, Integer>();
		destinationDemands = new HashMap<Node, Map<VehicleClass, Integer>>();
	}
	
	@Override
	public Node getOriginNode() {
		return origin;
	}
	
	@Override
	public int getDemand(VehicleClass vehicleClass) {
		return demands.get(vehicleClass).intValue();
	}
	
	@Override
	public Set<Node> getDestinationNodes() {
		return destinationDemands.keySet();
	}
	
	@Override
	public int getDemandByDestination(Node destination, VehicleClass vehicleClass) {
		return destinationDemands.get(destination) != null ?
				destinationDemands.get(destination).get(vehicleClass) : null;
	}
}
