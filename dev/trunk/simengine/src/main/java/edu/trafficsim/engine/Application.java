package edu.trafficsim.engine;

import java.util.List;

import edu.trafficsim.factory.SimulatorFactory;
import edu.trafficsim.model.core.Demand;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.simulator.Simulator;

public class Application {

	public static void main(String[] args) {

		Simulator simulator = SimulatorFactory.getInstance().createSimulator(100, 5);
		Builder builder = new Builder();
		Network network = builder.getNetwork();
		List<Demand> demands = builder.getDemands();
		Simulation simulation = new Simulation(simulator, network, demands);
		
		simulation.run();
	}
}
