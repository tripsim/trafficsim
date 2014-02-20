package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public class MultiKey<K1, K2> implements DataContainer,
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
		if (key1 == null || key2 == null)
			return false;
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
