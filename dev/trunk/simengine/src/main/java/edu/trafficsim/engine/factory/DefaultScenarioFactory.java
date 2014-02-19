package edu.trafficsim.engine.factory;

import edu.trafficsim.engine.ScenarioFactory;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.DefaultSimulationScenario;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultOd;
import edu.trafficsim.model.demand.DefaultOdMatrix;
import edu.trafficsim.model.simulator.DefaultSimulator;

public class DefaultScenarioFactory extends AbstractFactory implements
		ScenarioFactory {

	private static final String DEFAULT_NAME = "Default";
	private static final int DEFAULT_DURATION = 1000;
	private static final int DEFAULT_STEPSIZE = 1;

	private static DefaultScenarioFactory factory;

	private DefaultScenarioFactory() {
	}

	public static DefaultScenarioFactory getInstance() {
		if (factory == null) {
			factory = new DefaultScenarioFactory();
		}
		return factory;
	}

	@Override
	public DefaultSimulationScenario createSimulationScenario() {
		return createSimulationScenario(DEFAULT_NAME);
	}

	@Override
	public DefaultSimulationScenario createSimulationScenario(String name) {
		return createSimulationScenario(name, createSimulator(DEFAULT_NAME),
				null, createOdMatrix(DEFAULT_NAME), null);
	}

	@Override
	public DefaultSimulationScenario createSimulationScenario(String name,
			Simulator simulator, Network network, OdMatrix odMatrix,
			Router router) {
		return new DefaultSimulationScenario(nextId(), name, simulator,
				network, odMatrix, router);
	}

	@Override
	public DefaultOdMatrix createOdMatrix() {
		return createOdMatrix(DEFAULT_NAME);
	}

	@Override
	public DefaultOdMatrix createOdMatrix(String name) {
		return new DefaultOdMatrix(nextId(), name);
	}

	@Override
	public DefaultOd createOd(String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException {
		return new DefaultOd(nextId(), name, origin, destination,
				vehicleTypeComposition, driverTypeComposition,
				new double[] { 100 }, new Integer[] { 1000 });
	}

	@Override
	public DefaultOd createOd(String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		return new DefaultOd(nextId(), name, origin, destination,
				vehicleTypeComposition, driverTypeComposition, times, vphs);
	}

	@Override
	public Router createRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultSimulator createSimulator() {
		return createSimulator(DEFAULT_NAME);
	}

	@Override
	public DefaultSimulator createSimulator(String name) {
		return createSimulator(name, DEFAULT_DURATION, DEFAULT_STEPSIZE);
	}

	@Override
	public DefaultSimulator createSimulator(String name, int duration,
			int stepSize) {
		return new DefaultSimulator(nextId(), name, duration, stepSize);
	}

}
