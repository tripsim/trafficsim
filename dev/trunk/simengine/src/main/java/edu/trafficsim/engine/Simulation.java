package edu.trafficsim.engine;

import java.util.Set;

import edu.trafficsim.factory.RoadUserFactory;
import edu.trafficsim.factory.SimulatorFactory;
import edu.trafficsim.model.core.Agent;
import edu.trafficsim.model.core.Environment;
import edu.trafficsim.model.simulator.Simulator;

public class Simulation {
	
	private SimulatorFactory simulatorFactory;
	private RoadUserFactory roadUserFactory;

	private Simulator simulator;
	private Set<Agent> agents;
	private Set<Environment> environments;

	
	
	public Simulation(Set<Agent> agents, Set<Environment> environments) {
		simulatorFactory = SimulatorFactory.getInstance();
		roadUserFactory = RoadUserFactory.getInstance();
		
		this.agents = agents;
		this.environments = environments;
		
		// hack
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
