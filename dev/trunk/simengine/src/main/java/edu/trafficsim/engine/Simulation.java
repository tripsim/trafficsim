package edu.trafficsim.engine;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.trafficsim.factory.RoadUserFactory;
import edu.trafficsim.model.core.Agent;
import edu.trafficsim.model.core.Origin;
import edu.trafficsim.model.demand.VehicleGenerator;
import edu.trafficsim.model.demand.VehicleToBuild;
import edu.trafficsim.model.simulator.Simulator;

public class Simulation {
	private RoadUserFactory roadUserFactory;

	private Simulator simulator;
	private List<Origin> origins;
	private VehicleGenerator vehicleGenerator;
	
	public Simulation(Simulator simulator, List<Origin> origins, VehicleGenerator vehicleGenerator) {
		roadUserFactory = RoadUserFactory.getInstance();
		
		this.simulator = simulator;
		this.origins = origins;
		this.vehicleGenerator = vehicleGenerator;
	}
	
	public void run() {
		Set<Agent> agents = new HashSet<Agent>();
		Random rand = simulator.getRand();
		
		// time to live, indicates the remaining simulation steps
		int ttl = (int) Math.round(simulator.getDuration() / simulator.getStepSize());
		while (ttl > 0) {
			double time = simulator.getStepSize() * (double) ttl;
			for (Agent agent : agents) {
				if (agent.isActive())
					agent.stepForward();
				else
					agents.remove(agent);
			}
			for (Origin origin : origins) {
				VehicleToBuild vehicleToBuild = vehicleGenerator.getVehicleToBuild(origin, time, rand);
				roadUserFactory.createVehicle(vehicleToBuild, time, simulator.getStepSize());
			}
			ttl--;
		}
	}
}
