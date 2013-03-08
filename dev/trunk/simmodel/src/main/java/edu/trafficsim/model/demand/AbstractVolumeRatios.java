package edu.trafficsim.model.demand;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public abstract class AbstractVolumeRatios<T, S> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Node node;
	// Node -> VehicleClass -> Timestamp -> Ratio of the volumes go to the destination node
	// Timestamp in seconds
	protected Map<S, Map<VehicleClass, NavigableMap<Double, Double>>> ratios;

	public AbstractVolumeRatios(Node node) {
		this.node = node;
		ratios = new HashMap<S, Map<VehicleClass, NavigableMap<Double, Double>>>();
	}
	
	public Node getNode() {
		return node;
	}
	
	public double getRatio(S key, VehicleClass vehicleClass, double timestamp) {
		Map<VehicleClass, NavigableMap<Double, Double>> ratiosByClass = ratios.get(key);
		if (ratiosByClass == null)
			return 0;
		NavigableMap<Double, Double> ratiosByTimestamp = ratiosByClass.get(vehicleClass);
		if (ratiosByTimestamp == null)
			return 0;
		
		return ratiosByTimestamp.ceilingEntry(timestamp) == null ? 0 :
			ratiosByTimestamp.ceilingEntry(timestamp).getValue() == null ? 0 :
				ratiosByTimestamp.ceilingEntry(timestamp).getValue();
	}

	public void setRatio(S key, VehicleClass vehicleClass, double timestamp, double ratio) {
		Map<VehicleClass, NavigableMap<Double, Double>> ratiosByClass = ratios.get(key);
		if (ratiosByClass == null) {
			ratiosByClass = new HashMap<VehicleClass, NavigableMap<Double, Double>>();
			ratios.put(key, ratiosByClass);
		}
		NavigableMap<Double, Double> ratiosByTimestamp = ratiosByClass.get(vehicleClass);
		if (ratiosByTimestamp == null) {
			ratiosByTimestamp = new TreeMap<Double, Double>();
			ratiosByClass.put(vehicleClass, ratiosByTimestamp);
		}
		ratiosByTimestamp.put(timestamp, ratio);
	}
	
	

}
