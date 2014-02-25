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

	public SimulationScenario createSimulationScenario(Long id);

	public SimulationScenario createSimulationScenario(Long id, String name);

	public SimulationScenario createSimulationScenario(Long id, String name,
			Simulator simulator, Network network, OdMatrix odMatrix,
			Router router);

	public OdMatrix createOdMatrix(Long id);

	public OdMatrix createOdMatrix(Long id, String name);

	public Od createOd(Long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition) throws ModelInputException;

	public Od createOd(Long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	public Router createRouter(Long id);

	public Simulator createSimulator(Long id);

	public Simulator createSimulator(Long id, String name);

	public Simulator createSimulator(Long id, String name, int duration,
			int stepSize);

}
