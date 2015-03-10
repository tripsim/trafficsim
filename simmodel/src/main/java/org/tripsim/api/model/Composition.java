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
package org.tripsim.api.model;

import java.util.Collection;
import java.util.Set;

import org.tripsim.api.Nameable;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public interface Composition<T> extends Nameable {

	Set<T> keys();

	Collection<Double> values();

	double total();

	double probability(T t);

	void put(T key, double value);

	Double remove(T key);

	void culmulate(T key, double value);

	void reset();

	boolean isEmpty();

}
