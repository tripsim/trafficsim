package edu.trafficsim.engine;

import java.util.List;

import edu.trafficsim.factory.SimulatorFactory;
import edu.trafficsim.model.demand.Origin;
import edu.trafficsim.model.demand.VehicleGenerator;
import edu.trafficsim.model.simulator.Simulator;

public class Application {

	public static void main(String[] args) {

		Simulator simulator = SimulatorFactory.getInstance().createSimulator(100, 1);
		Builder builder = new Builder();
		List<Origin> origins = builder.getOrigins();
		VehicleGenerator vehicleGenerator = builder.getVehicleGenerator();
		Simulation simulation = new Simulation(simulator, origins, vehicleGenerator);
		
		simulation.run();
	}
}
