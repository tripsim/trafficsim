package edu.trafficsim.model.core;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;

public class DefaultSimulationScenario extends
		BaseEntity<DefaultSimulationScenario> implements SimulationScenario {

	private static final long serialVersionUID = 1L;

	private Simulator simulator;

	private Network network;
	private OdMatrix odMatrix;
	private Router router;

	public DefaultSimulationScenario(long id, String name, Simulator simulator,
			Network network, OdMatrix odMatrix) {
		this(id, name, simulator, network, odMatrix, null);
	}

	public DefaultSimulationScenario(long id, String name, Simulator simulator,
			Network network, OdMatrix odMatrix, Router router) {
		super(id, name);
		this.simulator = simulator;

		this.network = network;
		this.odMatrix = odMatrix;
		this.router = router;
	}

	@Override
	public Simulator getSimulator() {
		return simulator;
	}

	@Override
	public Network getNetwork() {
		return network;
	}

	@Override
	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	@Override
	public Router getRouter() {
		return router;
	}

}
