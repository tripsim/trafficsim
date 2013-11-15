package edu.trafficsim.engine.demo;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.plugin.ISimulation;
import edu.trafficsim.plugin.core.DefaultSimulation;

public class SimulationTest {

	private static SimulationTest test = null;

	private DemoBuilder builder;

	private SimulationTest() throws TransformException {
		try {
			builder = new DemoBuilder();
		} catch (ModelInputException e) {
			builder = null;
			e.printStackTrace();
		} catch (NoSuchAuthorityCodeException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}

	public static SimulationTest getInstance() throws TransformException {
		if (test == null)
			test = new SimulationTest();
		return test;
	}

	public StatisticsCollector run() throws ModelInputException,
			TransformException {
		SimulationScenario scenario = builder.getScenario();
		ISimulation simulation = new DefaultSimulation(scenario);

		simulation.run();
		return simulation.statistics();
	}

	public Network getNetwork() {
		return builder.getScenario().getNetwork();
	}
}
