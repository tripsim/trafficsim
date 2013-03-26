package edu.trafficsim.model.demand;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public abstract class AbstractTripProportion<T, S> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Node node;
	
	// used in NodeRouter and DestinedTripGenerator, where S should be Link and Node (Destination) respectively
	// proportions stored the proportion of whatever value (driven down by S -> vehicleClass -> timestamp (seconds)
	protected Map<S, Map<VehicleClass, NavigableMap<Double, Double>>> proportions;

	public AbstractTripProportion(Node node) {
		this.node = node;
		proportions = new HashMap<S, Map<VehicleClass, NavigableMap<Double, Double>>>();
	}
	
	public Node getNode() {
		return node;
	}
	
	public double getProportion(S key, VehicleClass vehicleClass, double timestamp) {
		Map<VehicleClass, NavigableMap<Double, Double>> proportionsByClass = proportions.get(key);
		if (proportionsByClass == null)
			return 0;
		NavigableMap<Double, Double> proportionsByTimestamp = proportionsByClass.get(vehicleClass);
		if (proportionsByTimestamp == null)
			return 0;
		
		return proportionsByTimestamp.ceilingEntry(timestamp) == null ? 0 :
			proportionsByTimestamp.ceilingEntry(timestamp).getValue() == null ? 0 :
				proportionsByTimestamp.ceilingEntry(timestamp).getValue();
	}

	public void setProportion(S key, VehicleClass vehicleClass, double timestamp, double proportion) {
		Map<VehicleClass, NavigableMap<Double, Double>> proportionsByClass = proportions.get(key);
		if (proportionsByClass == null) {
			proportionsByClass = new HashMap<VehicleClass, NavigableMap<Double, Double>>();
			proportions.put(key, proportionsByClass);
		}
		NavigableMap<Double, Double> proportionsByTimestamp = proportionsByClass.get(vehicleClass);
		if (proportionsByTimestamp == null) {
			proportionsByTimestamp = new TreeMap<Double, Double>();
			proportionsByClass.put(vehicleClass, proportionsByTimestamp);
		}
		proportionsByTimestamp.put(timestamp, proportion);
	}
	
}
