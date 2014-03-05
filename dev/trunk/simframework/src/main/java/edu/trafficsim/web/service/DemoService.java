package edu.trafficsim.web.service;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.demo.DemoSimulation;

@Service
public class DemoService {

	public SimulationScenario getScenario() throws TransformException {
		return DemoSimulation.getInstance().getScenario();
	}

}
