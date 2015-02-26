package edu.trafficsim.model;

import java.util.Collection;
import java.util.Set;

import edu.trafficsim.model.core.ModelInputException;

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

	public T getProperty(double time);

	public void setProperty(double time, T value);

	public T removeProperty(double time);

	public void setProperties(double[] times, T[] values)
			throws ModelInputException;

	public Set<Double> getJumpTimes();

	public Collection<T> getProperties();
}
