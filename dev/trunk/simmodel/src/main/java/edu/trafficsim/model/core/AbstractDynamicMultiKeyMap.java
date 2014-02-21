package edu.trafficsim.model.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDynamicMultiKeyMap<K1, K2, V> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final class DynamicMap<K1, K2, V> extends
			AbstractDynamicMap<MultiKey<K1, K2>, V> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	private final DynamicMap<K1, K2, V> properties;

	public AbstractDynamicMultiKeyMap() {
		properties = new DynamicMap<K1, K2, V>();
	}

	protected final V getProperty(K1 key1, K2 key2, double time) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		return properties.getProperty(key, time);
	}

	protected final void setProperty(K1 key1, K2 key2, double time, V value) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.setProperty(key, time, value);
	}

	protected final void removeProperty(K1 key1, K2 key2, double time) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.removeProperty(key, time);
	}

	protected final void removeProperty(K1 key1, K2 key2) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.removeProperty(key);
	}

	protected final Set<MultiKey<K1, K2>> keys() {
		return properties.keys();
	}

	protected final Set<K1> getPrimaryKeys(K2 key2) {
		Set<K1> set = new HashSet<K1>();
		for (MultiKey<K1, K2> multiKey : keys())
			if (multiKey.secondaryKey().equals(key2))
				set.add(multiKey.primaryKey());
		return set;
	}

	protected Set<K2> getSecondaryKeys(K1 key1) {
		Set<K2> set = new HashSet<K2>();
		for (MultiKey<K1, K2> multiKey : keys()) {
			if (multiKey.primaryKey().equals(key1))
				set.add(multiKey.secondaryKey());
		}
		return set;
	}

	@Override
	public String toString() {
		return properties.toString();
	}
}
