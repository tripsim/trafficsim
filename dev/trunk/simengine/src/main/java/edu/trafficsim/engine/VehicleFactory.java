package edu.trafficsim.engine;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;

public interface VehicleFactory {

	public Vehicle createVehicle(VehicleType vehicleType,
			DriverType driverType, SimulationScenario scenario)
			throws TransformException;;

}
