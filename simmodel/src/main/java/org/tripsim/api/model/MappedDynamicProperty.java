/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.api.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Utility to store time-based values with a key
 * 
 * The value is mapped by a key, and a time point;
 * 
 * @author Xuan
 *
 * @param <K>
 * @param <V>
 */
public interface MappedDynamicProperty<K, V> {

	public Set<K> keys();

	public V getProperty(K key, double time);

	public Collection<V> getProperties(K key);

	public void setProperty(K key, double time, V value);

	public void setProperties(K key, double[] times, V[] values);

	public V removeProperty(K key, double time);

	public Map<Double, V> removeProperty(K key);
}
