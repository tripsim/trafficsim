package edu.trafficsim.model.core;

import java.util.HashSet;
import java.util.Set;

import edu.trafficsim.model.DataContainer;


public abstract class AbstractDynamicMultiKeyMap<K1, K2, V> implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static final class MultiKey<K1, K2> extends BaseEntity<MultiKey<K1, K2>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private final K1 key1;
		private final K2 key2;
		
		public MultiKey(K1 key1, K2 key2) {
			this.key1 = key1;
			this.key2 = key2;
		}
		
		public K1 primaryKey() {
			return key1;
		}
		
		public K2 secondaryKey() {
			return key2;
		}
		
		@Override
		public int hashCode() {
			int hash = 17;
			hash = 31 * hash + key1.hashCode();
			hash = 37 * hash + key2.hashCode();
			return hash;
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			if (o == this)
	            return true;
	        if (!(o instanceof MultiKey))
	            return false;
	        return key1.equals(((MultiKey) o).primaryKey()) && key2.equals(((MultiKey) o).secondaryKey()) ?
	        		true : false;
		}
	}
	
	private static final class DynamicMap<K1, K2, V> extends AbstractDynamicMap<MultiKey<K1, K2>, V> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	private final DynamicMap<K1, K2, V> properties;
	
	public AbstractDynamicMultiKeyMap() {
		properties = new DynamicMap<K1, K2, V>();
	}
	
	protected V getProperty(K1 key1, K2 key2, double time) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		return properties.getProperty(key, time);
	}

	protected void setProperty(K1 key1, K2 key2, double time, V value) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.setProperty(key, time, value);
	}
	
	protected void removeProperty(K1 key1, K2 key2, double time) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.removeProperty(key, time);
	}
	
	protected void removeProperty(K1 key1, K2 key2) {
		MultiKey<K1, K2> key = new MultiKey<K1, K2>(key1, key2);
		properties.removeProperty(key);
	}
	
	protected Set<MultiKey<K1, K2>> keys() {
		return properties.keys();
	}
	
	protected Set<K1> getPrimaryKeys(K2 key2) {
		Set<K1> set = new HashSet<K1>();
		for (MultiKey<K1, K2> multiKey : keys())
			if(multiKey.secondaryKey().equals(key2))
				set.add(multiKey.primaryKey());
		return set;
	}
	
	protected Set<K2> getSecondaryKeys(K1 key1) {
		Set<K2> set = new HashSet<K2>();
		for (MultiKey<K1, K2> multiKey : keys()) {
			if(multiKey.primaryKey().equals(key1))
				set.add(multiKey.secondaryKey());
		}
		return set;
	}
	
}
