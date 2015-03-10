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
