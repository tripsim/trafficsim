package edu.trafficsim.plugin;

import java.util.List;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.Od;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;

public interface IVehicleGenerator {

	public List<Vehicle> getVehicles(Od od, Simulator simulator)
			throws TransformException;

}
