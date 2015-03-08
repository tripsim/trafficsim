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

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 * @param <S>
 *            the generic type
 */
public class Pair<T, S> implements Serializable, Comparable<Pair<T, S>> {

	private static final long serialVersionUID = 1L;

	private final T primary;
	private final S secondary;

	public static final <T, S> Pair<T, S> create(T t, S s) {
		return new Pair<T, S>(t, s);
	}

	public Pair(T key1, S key2) {
		this.primary = key1;
		this.secondary = key2;
	}

	public final T primary() {
		return primary;
	}

	public final S secondary() {
		return secondary;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + (primary == null ? 0 : primary.hashCode());
		hash = 31 * hash + (secondary == null ? 0 : secondary.hashCode());
		return hash;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public final boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Pair))
			return false;
		if (primary == null)
			return ((Pair) o).primary() != null ? false
					: secondary == null ? (((Pair) o).secondary() == null ? true
							: false)
							: secondary.equals(((Pair) o).secondary());
		if (secondary == null)
			return ((Pair) o).secondary() != null ? false : primary
					.equals(((Pair) o).primary());
		return primary.equals(((Pair) o).primary())
				&& secondary.equals(((Pair) o).secondary()) ? true : false;
	}

	@Override
	public String toString() {
		return "Pair [primary=" + primary + ", secondary=" + secondary + "]";
	}

	// TODO test its usefulness in tree map
	@Override
	public int compareTo(Pair<T, S> o) {
		if (o instanceof Pair)
			return primary.equals(o.primary) && secondary.equals(o.secondary) ? 0
					: hashCode() > o.hashCode() ? 1 : -1;
		return -1;
	}
}
