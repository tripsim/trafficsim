package edu.trafficsim.plugin;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.StatisticsCollector;

public interface ISimulating extends IPlugin {

	public void run(SimulationScenario simulationScenario,
			StatisticsCollector statistics) throws TransformException;

}
