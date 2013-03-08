package edu.trafficsim.engine;

import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.trafficsim.factory.RoadUserFactory;
import edu.trafficsim.model.core.Agent;
import edu.trafficsim.model.core.Demand;
import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.roadusers.Vehicle;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;
import edu.trafficsim.model.simulator.Simulator;

public class Simulation {
	private RoadUserFactory roadUserFactory;

	private Simulator simulator;
	private List<Demand> demands;
	private Set<Agent> agents;
	
	private Random rand;
	
	public Simulation(Simulator simulator, Network network, List<Demand> demands) {
		roadUserFactory = RoadUserFactory.getInstance();
		
		this.simulator = simulator;
		this.demands = demands;
		
		rand = new Random();
	}
	
	public void run() {
		// time to live, indicates the remaining simulation steps
		int ttl = (int) Math.round(simulator.getDuration() / simulator.getStepSize());
		int vphEq =(int) Math.round(3600d / simulator.getStepSize());
		while (ttl > 0) {
			double timestamp = simulator.getStepSize() * (double) ttl;
			for (Agent agent : agents)
				agent.stepForward();
			for (Demand demand : demands) {
				for (VehicleClass vehicleClass : demand.getVehicleClasses()) {
					// TODO find a way to generalize
					int vph = demand.getVph(vehicleClass, timestamp);
					if (rand.nextInt(vphEq) > vph) {
						Vehicle vehicle = roadUserFactory.createVehicle(vehicleClass, simulator.getStepSize());
						List<Lane> lanesToGo = demand.getOriginNode().getOutLanes();
						lanesToGo.get(rand.nextInt(lanesToGo.size())).addVehicle(vehicle);
					}
				}
			}
			ttl--;
		}
	}
}
