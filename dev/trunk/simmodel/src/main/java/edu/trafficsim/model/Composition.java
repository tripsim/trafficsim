package edu.trafficsim.model;

import java.util.Set;

public interface Composition<T> extends DataContainer {

	public Set<T> keys();

	public double total();

	public double probability(T t);
}
