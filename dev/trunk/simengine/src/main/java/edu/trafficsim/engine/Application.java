package edu.trafficsim.engine;

import edu.trafficsim.factory.SimulatorFactory;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.VehicleGenerator;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.simulator.Simulator;

public class Application {

	public static void main(String[] args) throws ModelInputException {

		Simulator simulator = SimulatorFactory.getInstance().createSimulator(
				100, 1);
		Builder builder = new Builder();
		Network network = builder.getNetwork();
		VehicleGenerator vehicleGenerator = builder.getVehicleGenerator();
		Simulation simulation = new Simulation(simulator, network,
				vehicleGenerator);

		simulation.run();
	}
}
