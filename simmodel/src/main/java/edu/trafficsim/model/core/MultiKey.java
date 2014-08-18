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

/**
 * 
 * 
 * @author Xuan Shi
 * @param <K1>
 *            the generic type
 * @param <K2>
 *            the generic type
 */
public class MultiKey<K1, K2> implements Serializable,
		Comparable<MultiKey<K1, K2>> {

	private static final long serialVersionUID = 1L;

	private final K1 key1;
	private final K2 key2;

	public MultiKey(K1 key1, K2 key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	public final K1 primaryKey() {
		return key1;
	}

	public final K2 secondaryKey() {
		return key2;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + (key1 == null ? 0 : key1.hashCode());
		hash = 31 * hash + (key2 == null ? 0 : key2.hashCode());
		return hash;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public final boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof MultiKey))
			return false;
		if (key1 == null)
			return ((MultiKey) o).primaryKey() != null ? false
					: key2 == null ? (((MultiKey) o).secondaryKey() == null ? true
							: false)
							: key2.equals(((MultiKey) o).secondaryKey());
		if (key2 == null)
			return ((MultiKey) o).secondaryKey() != null ? false : key1
					.equals(((MultiKey) o).primaryKey());
		return key1.equals(((MultiKey) o).primaryKey())
				&& key2.equals(((MultiKey) o).secondaryKey()) ? true : false;
	}

	@Override
	public String toString() {
		return key1.toString() + "," + key2.toString();
	}

	// TODO test its usefulness in tree map
	@Override
	public int compareTo(MultiKey<K1, K2> o) {
		if (o instanceof MultiKey)
			return key1.equals(o.key1) && key2.equals(o.key2) ? 0
					: hashCode() > o.hashCode() ? 1 : -1;
		return -1;
	}
}
