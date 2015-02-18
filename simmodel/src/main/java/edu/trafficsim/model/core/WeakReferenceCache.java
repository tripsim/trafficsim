package edu.trafficsim.model.core;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class WeakReferenceCache<K, V> {

	private Map<K, WeakReference<V>> cache = new HashMap<K, WeakReference<V>>();

	public V get(K key) {
		WeakReference<V> ref = cache.get(key);
		return ref == null ? null : ref.get();
	}

	public void put(K key, V value) {
		if (value == null) {
			return;
		}
		cache.put(key, new WeakReference<V>(value));
	}

	public void remove(K key) {
		cache.remove(key);
	}
}
