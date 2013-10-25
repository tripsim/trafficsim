package edu.trafficsim.engine;

import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;

public interface ScenarioBuilder {

	public SimulationScenario createSimulationScenario(OdMatrix odMatrix, Router router,
			VehicleGenerator vehicleGenerator, VehicleFactory vehicleFactory);

	public OdMatrix createEmptyOdMatrix(String name);

	public Od createOd(String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	public VehicleTypeComposition createVehicleTypeComposition(
			VehicleType[] vehicleTypes, double[] probabilities)
			throws ModelInputException;

	public DriverTypeComposition createDriverTypeComposition(
			DriverType[] driverTypes, double[] probabilities)
			throws ModelInputException;

	public Router createRouter();
}
