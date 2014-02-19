package edu.trafficsim.engine.demo;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.plugin.ISimulation;
import edu.trafficsim.plugin.core.DefaultSimulation;

public class DemoSimulation {

	private static DemoSimulation demo = null;

	private DemoBuilder builder;

	private DemoSimulation() throws TransformException {
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

	public static DemoSimulation getInstance() throws TransformException {
		if (demo == null)
			demo = new DemoSimulation();
		return demo;
	}

	public StatisticsCollector run() throws ModelInputException,
			TransformException {
		SimulationScenario scenario = builder.getScenario();
		ISimulation simulation = new DefaultSimulation(scenario);

		simulation.run();
		return simulation.statistics();
	}

	public SimulationScenario getScenario() {
		return builder.getScenario();
	}
}
