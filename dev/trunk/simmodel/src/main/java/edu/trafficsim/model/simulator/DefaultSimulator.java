package edu.trafficsim.model.simulator;

import edu.trafficsim.model.Simulator;

public class DefaultSimulator extends AbstractSimulator<DefaultSimulator>
		implements Simulator {

	private static final long serialVersionUID = 1L;

	private static final long DEFAULT_SEED = 0l;

	public DefaultSimulator(long id, String name, int duration,
			double stepSize) {
		this(id, name, duration, stepSize, DEFAULT_SEED);
	}

	public DefaultSimulator(long id, String name, int duration,
			double stepSize, long seed) {
		super(id, name, duration, stepSize, seed);
	}
}
