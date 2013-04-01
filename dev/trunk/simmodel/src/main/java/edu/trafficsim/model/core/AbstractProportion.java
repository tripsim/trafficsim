package edu.trafficsim.model.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.model.DataContainer;


public abstract class AbstractProportion<K> implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<K, Double> map;
	private double total;
	
	public AbstractProportion() {
		map = new HashMap<K, Double>();
	}
	
	public Set<K> keys() {
		return map.keySet();
	}
	
	public double get(K key) {
		return map.get(key);
	}

	public void put(K key, double value) {
		total -= map.get(key) != null ? map.get(key) : 0;
		map.put(key, value);
		total += value;
	}
	
	public void remove(K key) {
		Double value = map.remove(key);
		total -= value != null ? value : 0;
	}
	
	public double getProportion(K key) {
		return get(key) / total;
	}
	
}
