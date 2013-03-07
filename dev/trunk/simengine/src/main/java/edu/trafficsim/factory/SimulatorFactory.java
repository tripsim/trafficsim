package edu.trafficsim.factory;

import edu.trafficsim.model.simulator.Simulator;


public class SimulatorFactory extends AbstractFactory {

	private static SimulatorFactory factory;
	
	private SimulatorFactory() {
	}
	
	public static SimulatorFactory getInstance () {
		if (factory == null)
				factory = new SimulatorFactory();
		return factory;
	}
	
	public Simulator createSimulator (int duration, int stepSize) {
		return new Simulator(duration, stepSize);
	}
}
