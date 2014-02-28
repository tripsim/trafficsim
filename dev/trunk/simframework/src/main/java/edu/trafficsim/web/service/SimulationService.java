package edu.trafficsim.web.service;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.PluginManager;
import edu.trafficsim.web.SimulationResult;
import edu.trafficsim.web.service.entity.ScenarioService;

@Service
public class SimulationService {

	@Autowired
	SimulationResult result;
	@Autowired
	ScenarioService scenarioService;

	public void runSimulation() throws TransformException {
		SimulationScenario scenario = scenarioService.createScenario();

		ISimulating simulating = PluginManager.getSimulatingImpl(scenario
				.getSimulatingType(scenario.getSimulator().getSimulatorType()));
		simulating.run(scenario, result.getStatistics());
	}

}
