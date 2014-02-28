package edu.trafficsim.model.simulator;

import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.SimulatorType;

public class DefaultSimulator extends AbstractSimulator<DefaultSimulator>
		implements Simulator {

	private static final long serialVersionUID = 1L;

	private static final long DEFAULT_SEED = 0l;

	public DefaultSimulator(long id, String name, SimulatorType type,
			int duration, double stepSize) {
		this(id, name, type, duration, stepSize, DEFAULT_SEED);
	}

	public DefaultSimulator(long id, String name, SimulatorType type,
			int duration, double stepSize, long seed) {
		super(id, name, type, duration, stepSize, seed);
	}

}
