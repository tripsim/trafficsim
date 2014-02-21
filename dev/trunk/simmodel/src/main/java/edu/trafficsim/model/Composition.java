package edu.trafficsim.model;

import java.util.Set;

public interface Composition<T> extends DataContainer {

	public Set<T> keys();

	public double total();

	public double probability(T t);

	void put(T key, double value);

	void remove(T key);

	void culmulate(T key, double value);

	void reset();
}
