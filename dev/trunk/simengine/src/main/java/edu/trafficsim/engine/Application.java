package edu.trafficsim.engine;

import edu.trafficsim.factory.SimulatorFactory;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.simulator.Simulator;

public class Application {

	public static void main(String[] args) {

		Simulator simulator = SimulatorFactory.getInstance().createSimulator(100, 5);
		Network network = (new Builder()).getNetwork();
		Simulation simulation = new Simulation(simulator, network);
		
		simulation.run();
	}
}
