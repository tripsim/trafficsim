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
package org.tripsim.model.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.tripsim.api.model.Composition;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <K>
 *            the key type
 */
public abstract class AbstractComposition<K> implements Composition<K> {

	private static final long serialVersionUID = 1L;

	private String name;

	private Map<K, Double> map = new HashMap<K, Double>();
	private double total = 0;

	public AbstractComposition(String name, K[] keys, Double[] probabilities) {
		this.name = name;
		try {
			checkLength(keys, probabilities);
			for (int i = 0; i < keys.length; i++)
				culmulate(keys[i], probabilities[i]);
		} catch (RuntimeException e) {
			map.clear();
			total = 0;
		}
	}

	private void checkLength(K[] keys, Double[] probabilities) {
		if (keys == null || probabilities == null)
			throw new IllegalArgumentException(
					"keys and probabilities cannot be null!");
		else if (keys.length != probabilities.length)
			throw new IllegalArgumentException(
					"keys and composition need to have the same length!");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
	public void put(K key, double value) {
		total -= map.get(key) != null ? map.get(key) : 0;
		map.put(key, value);
		total += value;
	}

	@Override
	public void culmulate(K key, double value) {
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
