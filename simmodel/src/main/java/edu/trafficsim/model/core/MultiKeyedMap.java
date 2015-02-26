package edu.trafficsim.model.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MultiKeyedMap<K1, K2, V> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<MultiKey<K1, K2>, V> map;

	public MultiKeyedMap() {
		map = new HashMap<MultiKey<K1, K2>, V>();
	}

	public Set<K1> getPrimayKeys() {
		Set<K1> set = new HashSet<K1>();
		for (MultiKey<K1, K2> key : map.keySet()) {
			set.add(key.primaryKey());
		}
		return set;
	}

	public Set<K2> getSecondaryKeys() {
		Set<K2> set = new HashSet<K2>();
		for (MultiKey<K1, K2> key : map.keySet()) {
			set.add(key.secondaryKey());
		}
		return set;
	}

	public void put(K1 primaryKey, K2 secondaryKey, V value) {
		map.put(new MultiKey<K1, K2>(primaryKey, secondaryKey), value);
	}

	public V remove(K1 primaryKey, K2 secondaryKey) {
		return map.remove(new MultiKey<K1, K2>(primaryKey, secondaryKey));
	}

	public V get(K1 primaryKey, K2 secondaryKey) {
		return map.get(new MultiKey<K1, K2>(primaryKey, secondaryKey));
	}

	public Map<K2, V> getByPrimary(K1 primaryKey) {
		Map<K2, V> result = new HashMap<K2, V>();
		for (Entry<MultiKey<K1, K2>, V> entry : map.entrySet()) {
			MultiKey<K1, K2> key = entry.getKey();
			if (key.primaryKey().equals(primaryKey)) {
				result.put(key.secondaryKey(), entry.getValue());
			}
		}
		return result;
	}

	public Map<K1, V> getBySecondary(K2 secondaryKey) {
		Map<K1, V> result = new HashMap<K1, V>();
		for (Entry<MultiKey<K1, K2>, V> entry : map.entrySet()) {
			MultiKey<K1, K2> key = entry.getKey();
			if (key.secondaryKey().equals(secondaryKey)) {
				result.put(key.primaryKey(), entry.getValue());
			}
		}
		return result;
	}
}
