package edu.trafficsim.engine;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.CarFollowingType;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.LaneChangingType;
import edu.trafficsim.model.MovingType;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleBehavior;
import edu.trafficsim.model.VehicleType;

public interface VehicleFactory {

	public Vehicle createVehicle(VehicleSpecs vehicleSpecs, Simulator simulator)
			throws TransformException;

	public VehicleBehavior createBehavior(String name, MovingType movingType,
			CarFollowingType carFollowingType, LaneChangingType laneChangingType);

	public static class VehicleSpecs {

		public final VehicleType vehicleType;
		public final DriverType driverType;
		public final VehicleBehavior vehicleBehavior;

		public final Lane lane;

		public final double initSpeed;
		public final double initAccel;

		public VehicleSpecs(VehicleType vehicleType, DriverType driverType,
				VehicleBehavior vehicleBehavior, Lane lane, double initSpeed,
				double initAccel) {
			this.vehicleType = vehicleType;
			this.driverType = driverType;
			this.vehicleBehavior = vehicleBehavior;
			this.lane = lane;
			this.initSpeed = initSpeed;
			this.initAccel = initAccel;
		}
	}
}
