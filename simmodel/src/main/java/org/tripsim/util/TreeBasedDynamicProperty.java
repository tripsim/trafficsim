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
package org.tripsim.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.tripsim.api.model.DynamicProperty;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public class TreeBasedDynamicProperty<T> implements Serializable,
		DynamicProperty<T> {

	private static final long serialVersionUID = 1L;

	// time
	private final NavigableMap<Double, T> properties;

	public TreeBasedDynamicProperty() {
		properties = new TreeMap<Double, T>();
	}

	public final T getProperty(double time) {
		try {
			return properties.ceilingEntry(time).getValue();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public final void setProperty(double time, T value) {
		properties.put(time, value);
	}

	public final T removeProperty(double time) {
		return properties.remove(time);
	}

	public final void setProperties(double[] times, T[] values) {
		if (times == null || values == null)
			throw new IllegalArgumentException(
					"times or values  cannot be null!");
		else if (times.length != values.length)
			throw new IllegalArgumentException(
					"times and values needs to have the same length!");
		properties.clear();
		for (int i = 0; i < times.length; i++)
			setProperty(times[i], values[i]);
	}

	public final Set<Double> getJumpTimes() {
		return properties.keySet();
	}

	public final Collection<T> getProperties() {
		return properties.values();
	}

	@Override
	public String toString() {
		return String.valueOf(properties);
	}

}
