package edu.trafficsim.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MultiKeyedMap<K1, K2, V> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Pair<K1, K2>, V> map;

	public MultiKeyedMap() {
		map = new HashMap<Pair<K1, K2>, V>();
	}

	public Set<K1> getPrimayKeys() {
		Set<K1> set = new HashSet<K1>();
		for (Pair<K1, K2> key : map.keySet()) {
			set.add(key.primary());
		}
		return set;
	}

	public Set<K2> getSecondaryKeys() {
		Set<K2> set = new HashSet<K2>();
		for (Pair<K1, K2> key : map.keySet()) {
			set.add(key.secondary());
		}
		return set;
	}

	public void put(K1 primaryKey, K2 secondaryKey, V value) {
		map.put(new Pair<K1, K2>(primaryKey, secondaryKey), value);
	}

	public V remove(K1 primaryKey, K2 secondaryKey) {
		return map.remove(new Pair<K1, K2>(primaryKey, secondaryKey));
	}

	public V get(K1 primaryKey, K2 secondaryKey) {
		return map.get(new Pair<K1, K2>(primaryKey, secondaryKey));
	}

	public Map<K2, V> getByPrimary(K1 primaryKey) {
		Map<K2, V> result = new HashMap<K2, V>();
		for (Entry<Pair<K1, K2>, V> entry : map.entrySet()) {
			Pair<K1, K2> key = entry.getKey();
			if (key.primary().equals(primaryKey)) {
				result.put(key.secondary(), entry.getValue());
			}
		}
		return result;
	}

	public Map<K1, V> getBySecondary(K2 secondaryKey) {
		Map<K1, V> result = new HashMap<K1, V>();
		for (Entry<Pair<K1, K2>, V> entry : map.entrySet()) {
			Pair<K1, K2> key = entry.getKey();
			if (key.secondary().equals(secondaryKey)) {
				result.put(key.primary(), entry.getValue());
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return map.toString();
	}

}
