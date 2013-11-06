package edu.trafficsim.engine.factory;

import edu.trafficsim.engine.ScenarioBuilder;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.DefaultSimulationScenario;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultDriverTypeComposition;
import edu.trafficsim.model.demand.DefaultOd;
import edu.trafficsim.model.demand.DefaultOdMatrix;
import edu.trafficsim.model.demand.DefaultVehicleTypeComposition;
import edu.trafficsim.model.simulator.DefaultSimulator;

public class DefaultScenarioBuilder extends AbstractFactory implements
		ScenarioBuilder {

	private static DefaultScenarioBuilder factory;

	private DefaultScenarioBuilder() {
	}

	public static DefaultScenarioBuilder getInstance() {
		if (factory == null) {
			factory = new DefaultScenarioBuilder();
		}
		return factory;
	}

	@Override
	public SimulationScenario createSimulationScenario(String name,
			Simulator simulator, Network network, OdMatrix odMatrix,
			Router router) {
		return new DefaultSimulationScenario(nextId(), name, simulator,
				network, odMatrix, router);
	}

	@Override
	public OdMatrix createEmptyOdMatrix(String name) {
		return new DefaultOdMatrix(nextId(), name);
	}

	@Override
	public Od createOd(String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		return new DefaultOd(nextId(), name, origin, destination,
				vehicleTypeComposition, driverTypeComposition, times, vphs);
	}

	@Override
	public VehicleTypeComposition createVehicleTypeComposition(
			VehicleType[] vehicleTypes, double[] probabilities)
			throws ModelInputException {
		return new DefaultVehicleTypeComposition(vehicleTypes, probabilities);
	}

	@Override
	public DriverTypeComposition createDriverTypeComposition(
			DriverType[] driverTypes, double[] probabilities)
			throws ModelInputException {
		return new DefaultDriverTypeComposition(driverTypes, probabilities);
	}

	@Override
	public Router createRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultSimulator createSimulator(String name, int duration,
			int stepSize) {
		return new DefaultSimulator(nextId(), name, duration, stepSize);
	}

}
