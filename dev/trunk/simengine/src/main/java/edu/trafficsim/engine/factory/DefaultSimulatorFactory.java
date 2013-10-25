package edu.trafficsim.engine.factory;

import edu.trafficsim.engine.SimulatorFactory;
import edu.trafficsim.model.simulator.DefaultSimulator;

public class DefaultSimulatorFactory extends AbstractFactory implements
		SimulatorFactory {

	private static DefaultSimulatorFactory factory;

	private DefaultSimulatorFactory() {
	}

	public static DefaultSimulatorFactory getInstance() {
		if (factory == null)
			factory = new DefaultSimulatorFactory();
		return factory;
	}

	@Override
	public DefaultSimulator createSimulator(String name, int duration,
			int stepSize) {
		return new DefaultSimulator(nextId(), name, duration, stepSize);
	}
}
