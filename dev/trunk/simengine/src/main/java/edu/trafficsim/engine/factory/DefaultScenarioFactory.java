package edu.trafficsim.engine.factory;

import edu.trafficsim.engine.ScenarioFactory;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.SimulatorType;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.DefaultSimulationScenario;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultOd;
import edu.trafficsim.model.demand.DefaultOdMatrix;
import edu.trafficsim.model.demand.DefaultTurnPercentage;
import edu.trafficsim.model.simulator.DefaultSimulator;

public class DefaultScenarioFactory extends AbstractFactory implements
		ScenarioFactory {

	private static final String DEFAULT_NAME = "Default";

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
	public DefaultSimulationScenario createSimulationScenario(Long id,
			String name, Simulator simulator, Network network, OdMatrix odMatrix) {
		return new DefaultSimulationScenario(id, name, simulator, network,
				odMatrix);
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
	public DefaultTurnPercentage createTurnPercentage(Long id, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException {
		return new DefaultTurnPercentage(id, name, upstream, downstreams,
				percentages);
	}

	// TODO set default types
	private static SimulatorType simulatorType = new SimulatorType(0, "temp");

	@Override
	public DefaultSimulator createSimulator(Long id, String name, int duration,
			double stepSize) {
		return new DefaultSimulator(id, name, simulatorType, duration, stepSize);
	}

}
