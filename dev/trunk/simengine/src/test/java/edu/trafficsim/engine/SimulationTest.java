package edu.trafficsim.engine;

import edu.trafficsim.engine.factory.DefaultSimulatorFactory;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.core.ModelInputException;

public class SimulationTest {

	public static void main(String[] args) throws ModelInputException {

		Simulator simulator = DefaultSimulatorFactory.getInstance()
				.createSimulator(100, 1);
		Builder builder = new Builder();
		Network network = builder.getNetwork();
		VehicleGenerator vehicleGenerator = builder.getVehicleGenerator();
		VehicleFactory vehicleFactory = builder.getVehicleFactory();
		Simulation simulation = new Simulation(simulator, network,
				vehicleGenerator, vehicleFactory);

		simulation.run();
	}

}
