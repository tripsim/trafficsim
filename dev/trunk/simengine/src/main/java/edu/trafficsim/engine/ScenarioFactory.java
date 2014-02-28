package edu.trafficsim.engine;

import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

public interface ScenarioFactory {

	SimulationScenario createSimulationScenario(Long id, String name,
			Simulator simulator, Network network, OdMatrix odMatrix);

	OdMatrix createOdMatrix(Long id);

	OdMatrix createOdMatrix(Long id, String name);

	Od createOd(Long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException;

	Od createOd(Long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	TurnPercentage createTurnPercentage(Long id, String name, Link upstream,
			Link[] downstreams, double[] percentages)
			throws ModelInputException;

	Simulator createSimulator(Long id, String name, int duration,
			double stepSize);

}
