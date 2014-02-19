package edu.trafficsim.engine;

import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

public interface ScenarioFactory {

	public SimulationScenario createSimulationScenario();

	public SimulationScenario createSimulationScenario(String name);

	public SimulationScenario createSimulationScenario(String name,
			Simulator simulator, Network network, OdMatrix odMatrix,
			Router router);

	public OdMatrix createOdMatrix();

	public OdMatrix createOdMatrix(String name);

	public Od createOd(String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition);

	public Od createOd(String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	public Router createRouter();

	public Simulator createSimulator();

	public Simulator createSimulator(String name);

	public Simulator createSimulator(String name, int duration, int stepSize);

}
