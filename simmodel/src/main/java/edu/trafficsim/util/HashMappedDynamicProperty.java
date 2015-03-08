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
package edu.trafficsim.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.api.model.DynamicProperty;
import edu.trafficsim.api.model.MappedDynamicProperty;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class HashMappedDynamicProperty<K, V> implements Serializable,
		MappedDynamicProperty<K, V> {

	private static final long serialVersionUID = 1L;

	private final Map<K, DynamicProperty<V>> properties = new HashMap<K, DynamicProperty<V>>();

	public final Set<K> keys() {
		return properties.keySet();
	}

	@Override
	public final V getProperty(K key, double time) {
		return properties.get(key) == null ? null : properties.get(key)
				.getProperty(time);
	}

	@Override
	public Collection<V> getProperties(K key) {
		DynamicProperty<V> dp = properties.get(key);
		if (dp != null) {
			return dp.getProperties();
		}
		return Collections.emptyList();
	}

	@Override
	public final void setProperty(K key, double time, V value) {
		DynamicProperty<V> property = properties.get(key);
		if (property == null) {
			property = new TreeBasedDynamicProperty<V>();
			properties.put(key, property);
		}
		property.setProperty(time, value);
	}

	@Override
	public void setProperties(K key, double[] times, V[] values) {
		DynamicProperty<V> property = properties.get(key);
		if (property == null) {
			property = new TreeBasedDynamicProperty<V>();
			properties.put(key, property);
		}
		property.setProperties(times, values);
	}

	@Override
	public final V removeProperty(K key, double time) {
		DynamicProperty<V> property = properties.get(key);
		if (property != null) {
			return property.removeProperty(time);
		}
		return null;
	}

	@Override
	public final Map<Double, V> removeProperty(K key) {
		DynamicProperty<V> property = properties.remove(key);
		if (property != null) {
			Map<Double, V> result = new HashMap<Double, V>();
			for (Double time : property.getJumpTimes()) {
				result.put(time, property.getProperty(time));
			}
			return result;
		}
		return Collections.emptyMap();
	}

	@Override
	public String toString() {
		return String.valueOf(properties);
	}

}
