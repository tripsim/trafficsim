package edu.trafficsim.model.demand;

import java.util.Collections;
import java.util.Set;

import edu.trafficsim.model.core.AbstractDynamicMap;

class Flow extends AbstractDynamicMap<Destination, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final Destination NONE = new Destination(null);

	final Set<Destination> getDestinations() {
		return Collections.unmodifiableSet(keys());
	}

	final int getVph(Destination destination, double time) {
		return getProperty(destination, time);
	}

	final void setVph(Destination destination, double time, int vph) {
		setProperty(destination, time, vph);
	}

	final int getVph(double time) {
		int vph = 0;
		for (Destination destination : getDestinations())
			vph += getProperty(destination, time);
		return vph;
	}
}
