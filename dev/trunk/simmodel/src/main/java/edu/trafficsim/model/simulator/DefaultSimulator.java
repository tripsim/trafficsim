package edu.trafficsim.model.simulator;

import edu.trafficsim.model.Simulator;

public class DefaultSimulator extends AbstractSimulator<DefaultSimulator>
		implements Simulator {

	private static final long serialVersionUID = 1L;

	private static final long DEFAULT_SEED = 0l;

	public DefaultSimulator(double duration, double stepSize) {
		this(duration, stepSize, DEFAULT_SEED);
	}

	public DefaultSimulator(double duration, double stepSize, long seed) {
		super(duration, stepSize, seed);
	}
}
