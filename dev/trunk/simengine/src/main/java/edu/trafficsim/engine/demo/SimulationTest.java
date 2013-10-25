package edu.trafficsim.engine.demo;

import java.util.List;

import edu.trafficsim.engine.Simulation;
import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.core.DefaultSimulation;
import edu.trafficsim.engine.factory.DefaultSimulatorFactory;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.Colors;
import edu.trafficsim.model.core.ModelInputException;

public class SimulationTest {

	public static void main(String[] args) throws ModelInputException {
		List<Vehicle> vehicles = getInstance().run();
		for (Vehicle v : vehicles) {
			System.out.println(v.speed());
			System.out.println(Colors.getVehicleColor(v.speed()));
		}
	}

	private static SimulationTest test = null;

	private DemoBuilder builder;

	private SimulationTest() {
		try {
			builder = new DemoBuilder();
		} catch (ModelInputException e) {
			builder = null;
			e.printStackTrace();
		}
	}

	public static SimulationTest getInstance() {
		if (test == null)
			test = new SimulationTest();
		return test;
	}

	public List<Vehicle> run() throws ModelInputException {
		Simulator simulator = DefaultSimulatorFactory.getInstance()
				.createSimulator("test", 500, 1);

		Network network = builder.getNetwork();
		SimulationScenario scenario = builder.getScenario();

		Simulation simulation = new DefaultSimulation(simulator, network,
				scenario);

		return simulation.run();
	}

	public Network getNetwork() {
		return builder.getNetwork();
	}
}
