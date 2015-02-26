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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <K1>
 *            the generic type
 * @param <K2>
 *            the generic type
 * @param <V>
 *            the value type
 */
public abstract class MultiKeyedHashMappedDynamicProperty<K1, K2, V> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private final HashMappedDynamicProperty<MultiKey<K1, K2>, V> properties;

	public MultiKeyedHashMappedDynamicProperty() {
		properties = new HashMappedDynamicProperty<MultiKey<K1, K2>, V>();
	}

	public final V getProperty(K1 key1, K2 key2, double time) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		return properties.getProperty(key, time);
	}

	public final Collection<V> getPropertiesByPrimaaryKey(K1 primaryKey) {
		List<V> set = new ArrayList<V>();
		for (MultiKey<K1, K2> key : properties.keys()) {
			if (key.primaryKey().equals(primaryKey)) {
				set.addAll(properties.getProperties(key));
			}
		}
		return set;
	}

	public final Collection<V> getPropertiesBySecondaryKey(K2 secondaryKey) {
		List<V> set = new ArrayList<V>();
		for (MultiKey<K1, K2> key : properties.keys()) {
			if (key.secondaryKey().equals(secondaryKey)) {
				set.addAll(properties.getProperties(key));
			}
		}
		return set;
	}

	public final void setProperty(K1 key1, K2 key2, double time, V value) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.setProperty(key, time, value);
	}

	public final void setProperties(K1 key1, K2 key2, double times[], V[] values)
			throws ModelInputException {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.setProperties(key, times, values);
	}

	public final void removeProperty(K1 key1, K2 key2, double time) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.removeProperty(key, time);
	}

	public final void removeProperty(K1 key1, K2 key2) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.removeProperty(key);
	}

	public final Map<K2, Map<Double, V>> removePropertyByPrimaryKey(K1 key1) {
		Map<K2, Map<Double, V>> result = new HashMap<K2, Map<Double, V>>();
		for (MultiKey<K1, K2> multiKey : keys()) {
			if (multiKey.primaryKey().equals(key1)) {
				result.put(multiKey.secondaryKey(),
						properties.removeProperty(multiKey));
			}
		}
		return result;
	}

	public final Map<K1, Map<Double, V>> removePropertyBySecondaryKey(K2 key2) {
		Map<K1, Map<Double, V>> result = new HashMap<K1, Map<Double, V>>();
		for (MultiKey<K1, K2> multiKey : keys()) {
			if (multiKey.secondaryKey().equals(key2)) {
				result.put(multiKey.primaryKey(),
						properties.removeProperty(multiKey));
			}
		}
		return result;
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
