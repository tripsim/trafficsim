package edu.trafficsim.engine.demo;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.statistics.DefaultStatisticsCollector;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.core.DefaultSimulating;
import edu.trafficsim.utility.Sequence;

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
		StatisticsCollector statistics = DefaultStatisticsCollector.create();

		ISimulating simulation = new DefaultSimulating();

		simulation.run(scenario, statistics);
		return statistics;
	}

	public SimulationScenario getScenario() {
		return builder.getScenario();
	}

	public Sequence getSequence() {
		return builder.getSeq();
	}
}
