package edu.trafficsim.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.factory.VehicleFactory;
import edu.trafficsim.model.core.Agent;
import edu.trafficsim.model.demand.Origin;
import edu.trafficsim.model.demand.VehicleGenerator;
import edu.trafficsim.model.demand.VehicleToAdd;
import edu.trafficsim.model.network.Navigable;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.network.Path;
import edu.trafficsim.model.roadusers.Vehicle;
import edu.trafficsim.model.simulator.Simulator;
import edu.trafficsim.model.visitor.AgentVisitor;

public class Simulation {
	private VehicleFactory roadUserFactory;

	private Simulator simulator;
	private Network network;
	private VehicleGenerator vehicleGenerator;

	public Simulation(Simulator simulator, Network network,
			VehicleGenerator vehicleGenerator) {
		roadUserFactory = VehicleFactory.getInstance();

		this.network = network;
		this.simulator = simulator;
		this.vehicleGenerator = vehicleGenerator;
	}

	public void run() {
		System.out.println("******** Simulation Demo ********");
		System.out.println("---- Parameters ----");
		System.out.println("Random Seed: " + simulator.getSeed());
		System.out.println("Step Size: " + simulator.getStepSize());
		System.out.println("Duration: " + simulator.getDuration());

		AgentVisitor visitor = new AgentVisitor(network, simulator);

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		Random rand = simulator.getRand();

		// time to live, indicates the remaining simulation steps
		System.out.println("---- Simulation ----");

		int ttl = (int) Math.round(simulator.getDuration()
				/ simulator.getStepSize());
		while (ttl > 0) {
			double time = simulator.getDuration() - simulator.getStepSize()
					* (double) ttl;
			for (Navigable n : network.getAllLinks()) {
				for (Path p : n.getPaths()) {
					for (Vehicle v : p.getVehicles()) {
						v.apply(visitor);
						System.out.println("Time: " + time + "s: "
								+ v.getName() + " " + v.position());
					}
				}
			}
			for (Origin origin : network.getOrigins()) {
				for (VehicleToAdd vehicleToAdd : vehicleGenerator
						.getVehicleToAdd(origin, time, simulator.getStepSize(),
								rand)) {
					Vehicle vehicle = roadUserFactory.createVehicle(
							vehicleToAdd, time, simulator.getStepSize());
					vehicles.add(vehicle);
				}
			}
			ttl--;
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
	}
}
