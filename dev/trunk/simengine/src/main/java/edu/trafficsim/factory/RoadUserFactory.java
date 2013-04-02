package edu.trafficsim.factory;

import edu.trafficsim.model.demand.VehicleToAdd;
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
	public Vehicle createVehicle(VehicleToAdd vehicleToAdd, double startTime, double stepSize) {
		Vehicle vehicle = new Vehicle(vehicleToAdd.getVehicleType(), vehicleToAdd.getDriverType(), startTime, stepSize);
		vehicle.setSpeed(vehicleToAdd.getInitSpeed());
		vehicle.setAcceleration(vehicleToAdd.getInitAcceleration());
		vehicleToAdd.getLane().addVehicle(vehicle);
		return vehicle;
	}
	
}
