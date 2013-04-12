package edu.trafficsim.model.visitor;

import edu.trafficsim.model.core.MovingObject;
import edu.trafficsim.model.core.Visitor;
import edu.trafficsim.model.demand.Router;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.roadusers.Vehicle;
import edu.trafficsim.model.simulator.Simulator;


public class AgentVisitor implements Visitor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Network network;
	private Simulator simulator;
	
	public AgentVisitor(Network network, Simulator simulator) {
		this.network = network;
		this.simulator = simulator;
	}

	@Override
	public <T> void visit(MovingObject<T> m) {
		m.stepForward(simulator.getStepSize());
		if (m.beyondEnd())
			convey();
	}

	private void convey() {
		
	}

	@Override
	public <T> void visitNew(MovingObject<T> m) {
		if (m instanceof Vehicle) {
			Router router = network.getRouter(((Vehicle) m).getPath().getNavigable());
			
			 
		}
	}
}
