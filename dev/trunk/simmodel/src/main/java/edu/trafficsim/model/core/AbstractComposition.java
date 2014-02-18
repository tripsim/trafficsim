package edu.trafficsim.model.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Composition;

public abstract class AbstractComposition<K> extends BaseEntity<K> implements
		Composition<K> {

	private static final long serialVersionUID = 1L;

	private Map<K, Double> map = new HashMap<K, Double>();
	private double total;

	public AbstractComposition(long id, String name, K[] keys,
			double[] probabilities) throws ModelInputException {
		super(id, name);
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

	@Override
	public final void put(K key, double value) {
		total -= map.get(key) != null ? map.get(key) : 0;
		map.put(key, value);
		total += value;
	}

	@Override
	public final void culmulate(K key, double value) {
		total += value;
		value += map.get(key) != null ? map.get(key) : 0;
		map.put(key, value);
	}

	@Override
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

	@Override
	public void reset() {
		map.clear();
		total = 0;
	}

}
