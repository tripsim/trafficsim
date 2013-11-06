package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.factory.DefaultVehicleFactory;
import edu.trafficsim.model.Agent;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.IMoving;
import edu.trafficsim.plugin.IVehicleGenerator;

public class DefaultSimulation extends AbstractSimulation {

	protected static VehicleFactory vehicleFactory = DefaultVehicleFactory
			.getInstance();

	// TODO hack for plugin management
	protected static IMoving moving = new DefaultMoving();
	protected static IVehicleGenerator vehicleGenerator = new DefaultVehicleGenerator(
			vehicleFactory);

	public DefaultSimulation(SimulationScenario simulationScenario) {
		super(simulationScenario);
	}

	public void runDev() {

	}

	@Override
	public void run() {
		Simulator simulator = simulationScenario.getSimulator();
		System.out.println("******** Simulation Demo ********");
		System.out.println("---- Parameters ----");
		System.out.println("Random Seed: " + simulator.getSeed());
		System.out.println("Step Size: " + simulator.getStepSize());
		System.out.println("Duration: " + simulator.getDuration());

		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		// time to live, indicates the remaining simulation steps
		System.out.println("---- Simulation ----");

		// TODO move the logic to DEFAULT SIMULATOR
		while (!simulator.isFinished()) {
			double time = simulator.getForwarded();
			// for (Link link : network.getLinks()) {
			// for (Lane lane : link.getLanes()) {
			// for (Vehicle v : lane.getVehicles()) {
			// every lane a new thread for performance
			// }
			// }
			// }
			for (Vehicle v : vehicles) {
				moving.update(v, simulationScenario);
				System.out.println("Time: " + time + "s: " + v.getName() + " "
						+ v.position());
			}
			// for (Od od : network.getOds()) {
			// for (VehicleToAdd vehicleToAdd : vehicleGenerator
			// .getVehicleToAdd(od, simulator)) {
			// Vehicle vehicle = vehicleFactory.createVehicle(
			// vehicleToAdd, simulator);
			// vehicles.add(vehicle);
			// }
			// }
			simulator.stepForward();
		}

		System.out.println("---- Output ----");
		for (Agent agent : vehicles) {
			Vehicle v = (Vehicle) agent;
			System.out.print(v.getName() + ": ");

			// TODO write statistics collector
			// for (Coordinate c : v.trajectory()) {
			// System.out.print("(" + c.x + "," + c.y + ") ");
			// }
			System.out.println();
		}
	}

}
