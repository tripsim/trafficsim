package edu.trafficsim.engine;

import java.util.Set;

import edu.trafficsim.factory.RoadUserFactory;
import edu.trafficsim.model.core.Agent;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.simulator.Simulator;

public class Simulation {
	private RoadUserFactory roadUserFactory;

	private Simulator simulator;
	private Set<Agent> agents;
	

	
	
	public Simulation(Simulator simulator, Network network) {
		roadUserFactory = RoadUserFactory.getInstance();
		
		this.simulator = simulator = simulator;
	}
	
	public void run() {
		int ttl; // time to live, indicates the remaining simulation steps
		ttl = (int) (simulator.getDuration() / simulator.getStepSize());
		while (ttl > 0) {
			for (Agent agent : agents)
				agent.stepForward();
			ttl--;
		}
	}
}
