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
		router = null;
		odMatrix = null;
		simulator = null;
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
		if (simulator == null)
			scenarioFactory.createSimulator();
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
		if (odMatrix == null)
			odMatrix = scenarioFactory.createOdMatrix();
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

	public SimulationScenario createScenario(String name) {
		return scenarioFactory.createSimulationScenario(name, simulator,
				network, odMatrix, router);
	}
}
