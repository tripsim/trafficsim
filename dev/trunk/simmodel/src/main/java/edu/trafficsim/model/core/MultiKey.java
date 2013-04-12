package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public class MultiKey<K1, K2> implements DataContainer {

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

	public final K1 primaryKey() {
		return key1;
	}

	public final K2 secondaryKey() {
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
	public final boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof MultiKey))
			return false;
		return key1.equals(((MultiKey) o).primaryKey())
				&& key2.equals(((MultiKey) o).secondaryKey()) ? true : false;
	}
}
