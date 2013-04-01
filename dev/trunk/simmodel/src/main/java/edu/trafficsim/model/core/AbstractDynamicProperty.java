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
	private NavigableMap<Double, T> properties;
	
	public AbstractDynamicProperty() {
		properties = new TreeMap<Double, T>();
	}
	
	protected T getProperty(double time) {
		return properties.ceilingEntry(time).getValue();
	}
	
	protected void setProperty(double time, T value) {
		properties.put(time, value);
	}
	
	protected void removeProperty(double time) {
		properties.remove(time);
	}
	
}
