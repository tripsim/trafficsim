package edu.trafficsim.model;

import java.util.Collection;
import java.util.Set;

import edu.trafficsim.model.core.ModelInputException;

public interface Composition<T> extends DataContainer {

	Set<T> keys();

	Collection<Double> values();

	double total();

	double probability(T t);

	void put(T key, double value) throws ModelInputException;

	Double remove(T key);

	void culmulate(T key, double value) throws ModelInputException;

	void reset();

	boolean isEmpty();

}
