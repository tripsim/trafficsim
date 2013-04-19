package edu.trafficsim.engine.demo;

import java.util.List;

import edu.trafficsim.engine.Simulation;
import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.VehicleGenerator;
import edu.trafficsim.engine.factory.DefaultSimulatorFactory;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.ModelInputException;

public class SimulationTest {

	public static void main(String[] args) throws ModelInputException {
		run();
	}

	public static List<Vehicle> run() throws ModelInputException {
		Simulator simulator = DefaultSimulatorFactory.getInstance()
				.createSimulator(500, 1);
		Builder builder = new Builder();
		Network network = builder.getNetwork();
		VehicleGenerator vehicleGenerator = builder.getVehicleGenerator();
		VehicleFactory vehicleFactory = builder.getVehicleFactory();
		Simulation simulation = new Simulation(simulator, network,
				vehicleGenerator, vehicleFactory);

		return simulation.run();
	}

}
