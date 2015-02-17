package edu.trafficsim.engine.statistics;

import java.util.HashMap;
import java.util.Map;

public class StatisticsFrames<T> {

	long simulationId;
	
	final Map<Long, T> states;

	public StatisticsFrames() {
		states = new HashMap<Long, T>();
	}

	public T getStates(Long id) {
		return states.get(id);
	}

	void addState(long sequence, T state) {
		states.put(sequence, state);
	}

	T getState(long sequence) {
		return states.get(sequence);
	}
}
