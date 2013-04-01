package edu.trafficsim.factory;

import edu.trafficsim.model.demand.VehicleToBuild;
import edu.trafficsim.model.roadusers.Vehicle;

public class RoadUserFactory extends AbstractFactory {
	
	private static RoadUserFactory factory;
	
	private RoadUserFactory() {
	}
	
	public static RoadUserFactory getInstance() {
		if (factory == null)
			factory = new RoadUserFactory();
		return factory;
	}
	
	// TODO complete the creation
	public Vehicle createVehicle(VehicleToBuild vehicleToBuild, double startTime, double stepSize) {
		Vehicle vehicle = new Vehicle(vehicleToBuild.getVehicleType(), vehicleToBuild.getDriverType(), startTime, stepSize);
		vehicle.setSpeed(vehicleToBuild.getInitSpeed());
		vehicle.setAcceleration(vehicleToBuild.getInitAcceleration());
		return vehicle;
	}
	
}
