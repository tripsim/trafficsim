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
	public DefaultSimulationScenario createSimulationScenario(Long id) {
		return createSimulationScenario(id, DEFAULT_NAME);
	}

	@Override
	public DefaultSimulationScenario createSimulationScenario(Long id,
			String name) {
		return createSimulationScenario(id, name,
				createSimulator(id, DEFAULT_NAME), null,
				createOdMatrix(id, DEFAULT_NAME), null);
	}

	@Override
	public DefaultSimulationScenario createSimulationScenario(Long id,
			String name, Simulator simulator, Network network,
			OdMatrix odMatrix, Router router) {
		return new DefaultSimulationScenario(id, name, simulator, network,
				odMatrix, router);
	}

	@Override
	public DefaultOdMatrix createOdMatrix(Long id) {
		return createOdMatrix(id, DEFAULT_NAME);
	}

	@Override
	public DefaultOdMatrix createOdMatrix(Long id, String name) {
		return new DefaultOdMatrix(id, name);
	}

	@Override
	public DefaultOd createOd(Long id, String name, Node origin,
			Node destination, VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException {
		return new DefaultOd(id, name, origin, destination,
				vehicleTypeComposition, driverTypeComposition,
				new double[] { 100 }, new Integer[] { 1000 });
	}

	@Override
	public DefaultOd createOd(Long id, String name, Node origin,
			Node destination, VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		return new DefaultOd(id, name, origin, destination,
				vehicleTypeComposition, driverTypeComposition, times, vphs);
	}

	@Override
	public Router createRouter(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultSimulator createSimulator(Long id) {
		return createSimulator(id, DEFAULT_NAME);
	}

	@Override
	public DefaultSimulator createSimulator(Long id, String name) {
		return createSimulator(id, name, DEFAULT_DURATION, DEFAULT_STEPSIZE);
	}

	@Override
	public DefaultSimulator createSimulator(Long id, String name, int duration,
			int stepSize) {
		return new DefaultSimulator(id, name, duration, stepSize);
	}

}
