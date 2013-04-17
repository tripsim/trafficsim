package edu.trafficsim.engine;

import edu.trafficsim.model.Simulator;

public interface SimulatorFactory {

	public Simulator createSimulator(int duration, int stepSize);

}
