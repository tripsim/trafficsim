package edu.trafficsim.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.linearref.LengthLocationMap;

import edu.trafficsim.factory.RoadUserFactory;
import edu.trafficsim.model.core.Agent;
import edu.trafficsim.model.demand.Origin;
import edu.trafficsim.model.demand.VehicleGenerator;
import edu.trafficsim.model.demand.VehicleToAdd;
import edu.trafficsim.model.roadusers.Vehicle;
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
		System.out.println("******** Simulation Demo ********");
		System.out.println("---- Parameters ----");
		System.out.println("Random Seed: " + simulator.getSeed());
		System.out.println("Step Size: " + simulator.getStepSize());
		System.out.println("Duration: " + simulator.getDuration());
		Set<Agent> agents = new HashSet<Agent>();
		List<Agent> agentsToRemove = new ArrayList<Agent>();
		Random rand = simulator.getRand();
		
		// time to live, indicates the remaining simulation steps
		System.out.println("---- Simulation ----");
		
		int ttl = (int) Math.round(simulator.getDuration() / simulator.getStepSize());
		while (ttl > 0) {
			double time = simulator.getDuration() - simulator.getStepSize() * (double) ttl;
			
			for (Agent agent : agents) {
				if (agent.isActive()) {
					agent.stepForward(simulator.getStepSize());

					// HACK to show result
					Vehicle v = (Vehicle) agent;
					System.out.print("Time: " + time + "s: " + v.getName() + " ");
					for (Double d : v.getPositions())
						System.out.print(d + " ");
					System.out.println();
				}
				else
					agentsToRemove.add(agent);
			}
			agents.removeAll(agentsToRemove);
//			agentsToRemove.clear();
			
			for (Origin origin : origins) {
				for (VehicleToAdd vehicleToAdd : vehicleGenerator.getVehicleToAdd(origin, time, simulator.getStepSize(), rand)) {
					Vehicle vehicle = roadUserFactory.createVehicle(vehicleToAdd, time, simulator.getStepSize());
					agents.add(vehicle);
				}
			}
			ttl--;
		}
		

		System.out.println("---- Output ----");
		for (Agent agent : agentsToRemove) {
			Vehicle v = (Vehicle) agent;
			System.out.print(v.getName() + ": ");
			// HACK to show coordinates
			for (Double d : v.getPositions()) {
				LineString linearGeom = v.getLane().getLink().getCenterLine();
				Coordinate coord = LengthLocationMap.getLocation(linearGeom, d).getCoordinate(linearGeom);
				System.out.print("(" + coord.x + "," + coord.y + ") ");
			}
			System.out.println();
		}
	}
}
