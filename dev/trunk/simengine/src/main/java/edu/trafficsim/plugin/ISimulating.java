package edu.trafficsim.plugin;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.model.SimulationScenario;

public interface ISimulating {

	public void run(SimulationScenario simulationScenario,
			StatisticsCollector statistics) throws TransformException;

}
