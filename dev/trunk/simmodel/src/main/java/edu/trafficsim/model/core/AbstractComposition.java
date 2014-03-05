package edu.trafficsim.model.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Composition;

public abstract class AbstractComposition<K> extends BaseEntity<K> implements
		Composition<K> {

	private static final long serialVersionUID = 1L;

	private Map<K, Double> map = new HashMap<K, Double>();
	private double total = 0;

	public AbstractComposition(long id, String name, K[] keys,
			Double[] probabilities) {
		super(id, name);
		try {
			checkLength(keys, probabilities);
			for (int i = 0; i < keys.length; i++)
				culmulate(keys[i], probabilities[i]);
		} catch (ModelInputException e) {
			map.clear();
			total = 0;
		}
	}

	private void checkLength(K[] keys, Double[] probabilities)
			throws ModelInputException {
		if (keys == null || probabilities == null)
			throw new ModelInputException(
					"keys and probabilities cannot be null!");
		else if (keys.length != probabilities.length)
			throw new ModelInputException(
					"keys and composition need to have the same length!");
	}

	@Override
	public final Set<K> keys() {
		return Collections.unmodifiableSet(map.keySet());
	}

	public final Double get(K key) {
		return map.get(key);
	}

	@Override
	public final Collection<Double> values() {
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public void put(K key, double value) throws ModelInputException {
		total -= map.get(key) != null ? map.get(key) : 0;
		map.put(key, value);
		total += value;
	}

	@Override
	public void culmulate(K key, double value) throws ModelInputException {
		total += value;
		value += map.get(key) != null ? map.get(key) : 0;
		map.put(key, value);
	}

	@Override
	public final Double remove(K key) {
		Double value = map.remove(key);
		total -= value != null ? value : 0;
		return value;
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

	@Override
	public String toString() {
		return super.toString() + ":" + map.toString();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

}
