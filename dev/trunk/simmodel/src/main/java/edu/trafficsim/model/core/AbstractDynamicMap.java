package edu.trafficsim.model.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDynamicMap<K, V> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final class DynamicProperty<V> extends
			AbstractDynamicProperty<V> {

		private static final long serialVersionUID = 1L;

	}

	private final Map<K, DynamicProperty<V>> properties;

	public AbstractDynamicMap() {
		properties = new HashMap<K, DynamicProperty<V>>();
	}

	protected final Set<K> keys() {
		return properties.keySet();
	}

	protected final V getProperty(K key, double time) {
		return properties.get(key) == null ? null : properties.get(key)
				.getProperty(time);
	}

	protected final void setProperty(K key, double time, V value) {
		DynamicProperty<V> property = properties.get(key);
		if (property == null) {
			property = new DynamicProperty<V>();
			properties.put(key, property);
		}
		property.setProperty(time, value);
	}

	protected final void removeProperty(K key, double time) {
		DynamicProperty<V> property = properties.get(key);
		if (property != null) {
			property.removeProperty(time);
		}
	}

	protected final void removeProperty(K key) {
		properties.remove(key);
	}

	@Override
	public String toString() {
		return properties.toString();
	}
}
