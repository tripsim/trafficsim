package edu.trafficsim.web;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.ScenarioFactory;
import edu.trafficsim.engine.factory.DefaultNetworkFactory;
import edu.trafficsim.engine.factory.DefaultScenarioFactory;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SimulationProject {

	private final static String ENTITY_NAME = "TEST";

	// TODO set factory through settings
	private NetworkFactory networkFactory;
	private ScenarioFactory scenarioFactory;

	private Simulator simulator;
	private Network network;
	private OdMatrix odMatrix;
	private Router router;

	public SimulationProject() {
		networkFactory = DefaultNetworkFactory.getInstance();
		scenarioFactory = DefaultScenarioFactory.getInstance();

		reset();
	}

	public void reset() {
		network = null;
		odMatrix = scenarioFactory.createEmptyOdMatrix(ENTITY_NAME);
		router = null;
	}

	public NetworkFactory getNetworkFactory() {
		return networkFactory;
	}

	public void setNetworkFactory(NetworkFactory networkFactory) {
		this.networkFactory = networkFactory;
	}

	public ScenarioFactory getScenarioFactory() {
		return scenarioFactory;
	}

	public void setScenarioFactory(ScenarioFactory scenarioFactory) {
		this.scenarioFactory = scenarioFactory;
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	public void setOdMatrix(OdMatrix odMatrix) {
		this.odMatrix = odMatrix;
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public SimulationScenario getScenario() {
		return scenarioFactory.createSimulationScenario(ENTITY_NAME, simulator,
				network, odMatrix, router);
	}
}
