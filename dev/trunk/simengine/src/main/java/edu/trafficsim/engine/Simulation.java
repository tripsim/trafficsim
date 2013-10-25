package edu.trafficsim.engine;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.engine.generator.VehicleToAdd;
import edu.trafficsim.model.Agent;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;

public class Simulation {
	private Simulator simulator;
	private Network network;
	private VehicleGenerator vehicleGenerator;
	private VehicleFactory vehicleFactory;

	public Simulation(Simulator simulator, Network network,
			VehicleGenerator vehicleGenerator, VehicleFactory vehicleFactory) {
		this.network = network;
		this.simulator = simulator;
		this.vehicleGenerator = vehicleGenerator;
		this.vehicleFactory = vehicleFactory;
	}

	public List<Vehicle> run() {
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
				v.stepForward(simulator);
				System.out.println("Time: " + time + "s: " + v.getName() + " "
						+ v.position());
			}
//			for (Od od : network.getOds()) {
//				for (VehicleToAdd vehicleToAdd : vehicleGenerator
//						.getVehicleToAdd(od, simulator)) {
//					Vehicle vehicle = vehicleFactory.createVehicle(
//							vehicleToAdd, simulator);
//					vehicles.add(vehicle);
//				}
//			}
			simulator.stepForward();
		}

		System.out.println("---- Output ----");
		for (Agent agent : vehicles) {
			Vehicle v = (Vehicle) agent;
			System.out.print(v.getName() + ": ");
			for (Coordinate c : v.trajectory()) {
				System.out.print("(" + c.x + "," + c.y + ") ");
			}
			System.out.println();
		}
		return vehicles;
	}
}
