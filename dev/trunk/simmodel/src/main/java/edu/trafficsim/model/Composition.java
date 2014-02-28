package edu.trafficsim.model;

import java.util.Set;

import edu.trafficsim.model.core.ModelInputException;

public interface Composition<T> extends DataContainer {

	Set<T> keys();

	double total();

	double probability(T t);

	void put(T key, double value) throws ModelInputException;

	void remove(T key);

	void culmulate(T key, double value) throws ModelInputException;

	void reset();
}
