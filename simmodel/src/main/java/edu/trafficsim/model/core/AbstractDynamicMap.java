/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.model.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public abstract class AbstractDynamicMap<K, V> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 * @param <V>
	 *            the value type
	 */
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
