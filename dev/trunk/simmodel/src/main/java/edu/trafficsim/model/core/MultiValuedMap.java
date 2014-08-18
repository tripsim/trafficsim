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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
public class MultiValuedMap<K, V> implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Map<K, Set<V>> map;

	private static final int DEFAULT_INITIAL_CAPACITY = 10;

	public MultiValuedMap() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public MultiValuedMap(int capacity) {
		map = new HashMap<K, Set<V>>(capacity);
	}

	public Set<K> keys() {
		return map.keySet();
	}

	public Collection<V> values() {
		List<V> list = new ArrayList<V>();
		for (Set<V> s : map.values()) {
			list.addAll(s);
		}
		return Collections.unmodifiableCollection(list);
	}

	public void add(K key, V value) {
		Set<V> values = map.get(key);
		if (values == null) {
			map.put(key, values = new HashSet<V>());
		}
		values.add(value);
	}

	public void remove(K key, V value) {
		Set<V> s = map.get(key);
		if (s != null)
			s.remove(value);
	}

	public void set(K key, Set<V> values) {
		map.put(key, values);
	}

	public Set<V> get(K key) {
		if (map.get(key) != null)
			return map.get(key);
		return Collections.emptySet();
	}

	public Map<K, Set<V>> asMap() {
		return Collections.unmodifiableMap(map);
	}
}
