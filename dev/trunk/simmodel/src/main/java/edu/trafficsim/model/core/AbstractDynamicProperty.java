package edu.trafficsim.model.core;

import java.util.NavigableMap;
import java.util.TreeMap;

import edu.trafficsim.model.DataContainer;

public abstract class AbstractDynamicProperty<T> implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// time
	private final NavigableMap<Double, T> properties;

	public AbstractDynamicProperty() {
		properties = new TreeMap<Double, T>();
	}

	protected final T getProperty(double time) {
		return properties.ceilingEntry(time).getValue();
	}

	protected final void setProperty(double time, T value) {
		properties.put(time, value);
	}

	protected final void removeProperty(double time) {
		properties.remove(time);
	}

}
