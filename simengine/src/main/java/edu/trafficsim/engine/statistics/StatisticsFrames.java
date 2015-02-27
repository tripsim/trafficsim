package edu.trafficsim.engine.statistics;

import java.util.Collection;
import java.util.Set;

import edu.trafficsim.model.core.MultiKeyedMap;

public class StatisticsFrames<T> {

	String simulationName;

	final MultiKeyedMap<Long, Long, T> states;

	StatisticsFrames() {
		states = new MultiKeyedMap<Long, Long, T>();
	}

	public Set<Long> getIds() {
		return states.getPrimayKeys();
	}

	public Set<Long> getSequences() {
		return states.getSecondaryKeys();
	}

	public T getState(long id, long sequence) {
		return states.get(id, sequence);
	}

	public Collection<T> getStatesById(long id) {
		return states.getByPrimary(id).values();
	}

	public Collection<T> getStatesBySequence(long sequence) {
		return states.getBySecondary(sequence).values();
	}

	void addState(long id, long sequence, T state) {
		states.put(id, sequence, state);
	}

	public String getSimulationName() {
		return simulationName;
	}

}
