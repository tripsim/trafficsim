package edu.trafficsim.plugin;

import java.util.List;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;

public interface IVehicleGenerating {

	public List<Vehicle> newVehicles(Od od, SimulationScenario scenario,
			VehicleFactory vehicleFactory) throws TransformException;

}