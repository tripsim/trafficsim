package edu.trafficsim.model.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.model.Composition;

public abstract class AbstractComposition<K> implements Composition<K> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<K, Double> map = new HashMap<K, Double>();
	private double total;

	public AbstractComposition(K[] keys, double[] probabilities)
			throws ModelInputException {
		if (keys == null || probabilities == null)
			throw new ModelInputException(
					"keys and probabilities cannot be null!");
		else if (keys.length != probabilities.length)
			throw new ModelInputException(
					"keys and composition need to have the same length!");
		for (int i = 0; i < keys.length; i++)
			put(keys[i], probabilities[i]);
	}

	@Override
	public final Set<K> keys() {
		return map.keySet();
	}

	public final double get(K key) {
		return map.get(key);
	}

	public final void put(K key, double value) {
		total -= map.get(key) != null ? map.get(key) : 0;
		map.put(key, value);
		total += value;
	}

	public final void remove(K key) {
		Double value = map.remove(key);
		total -= value != null ? value : 0;
	}

	@Override
	public double total() {
		return total;
	}

	@Override
	public final double probability(K key) {
		return get(key) / total;
	}

}
