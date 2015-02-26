package edu.trafficsim.engine.statistics;

import edu.trafficsim.model.core.MultiKeyedMap;

public class StatisticsFrames<T> {

	String simulationName;

	final MultiKeyedMap<Long, Long, T> states;

	public StatisticsFrames() {
		states = new MultiKeyedMap<Long, Long, T>();
	}

	public T getState(Long id, Long sequence) {
		return states.get(id, sequence);
	}

	void addState(long id, long sequence, T state) {
		states.put(id, sequence, state);
	}

	public String getSimulationName() {
		return simulationName;
	}


}
