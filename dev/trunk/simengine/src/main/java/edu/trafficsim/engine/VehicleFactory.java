package edu.trafficsim.engine;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;

public interface VehicleFactory {

	public Vehicle createVehicle(VehicleSpecs vehicleSpecs,
			SimulationScenario scenario) throws TransformException;;

	public static class VehicleSpecs {

		public final VehicleType vehicleType;
		public final DriverType driverType;

		public final Lane lane;

		public final double initSpeed;
		public final double initAccel;

		public VehicleSpecs(VehicleType vehicleType, DriverType driverType,
				Lane lane, double initSpeed, double initAccel) {
			this.vehicleType = vehicleType;
			this.driverType = driverType;
			this.lane = lane;
			this.initSpeed = initSpeed;
			this.initAccel = initAccel;
		}
	}
}
