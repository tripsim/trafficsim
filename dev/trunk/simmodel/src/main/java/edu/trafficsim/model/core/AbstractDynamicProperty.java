package edu.trafficsim.model.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public abstract class AbstractDynamicProperty<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// time
	private final NavigableMap<Double, T> properties;

	public AbstractDynamicProperty() {
		properties = new TreeMap<Double, T>();
	}

	public final T getProperty(double time) {
		try {
			return properties.ceilingEntry(time).getValue();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public final void setProperty(double time, T value) {
		properties.put(time, value);
	}

	public final void removeProperty(double time) {
		properties.remove(time);
	}

	public final void setProperties(double[] times, T[] values)
			throws ModelInputException {
		if (times == null || values == null)
			throw new ModelInputException("times or values  cannot be null!");
		else if (times.length != values.length)
			throw new ModelInputException(
					"times and values needs to have the same length!");
		properties.clear();
		for (int i = 0; i < times.length; i++)
			setProperty(times[i], values[i]);
	}

	public final Set<Double> getJumpTimes() {
		return properties.keySet();
	}

	public final Collection<T> getValues() {
		return properties.values();
	}

	@Override
	public String toString() {
		return properties.toString();
	}
}
