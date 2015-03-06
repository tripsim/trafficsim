package edu.trafficsim.api.model;

import java.util.Collection;
import java.util.Set;

/**
 * 
 * Utility to store time-based property value.
 * 
 * The property is constructed on a series of time and value points.
 * 
 * @author Xuan
 *
 * @param <T>
 */
public interface DynamicProperty<T> {

	T getProperty(double time);

	void setProperty(double time, T value);

	T removeProperty(double time);

	void setProperties(double[] times, T[] values);

	Set<Double> getJumpTimes();

	Collection<T> getProperties();
}
