package edu.trafficsim.factory;

import java.util.NoSuchElementException;

import edu.trafficsim.model.demand.VehicleToAdd;
import edu.trafficsim.model.roadusers.Vehicle;

public class RoadUserFactory extends AbstractFactory {
	
	private static RoadUserFactory factory;
	
	private static int count = 0;
	
	private RoadUserFactory() {
	}
	
	public static RoadUserFactory getInstance() {
		if (factory == null)
			factory = new RoadUserFactory();
		return factory;
	}
	
	// TODO complete the creation
	public Vehicle createVehicle(VehicleToAdd vehicleToAdd, double startTime, double stepSize) {
		count++;
		Vehicle vehicle = new Vehicle(vehicleToAdd.getVehicleType(), vehicleToAdd.getDriverType(), startTime, stepSize);
		vehicle.setSpeed(vehicleToAdd.getInitSpeed());
		vehicle.setAcceleration(vehicleToAdd.getInitAcceleration());
		double position = 0;
		try {
			Vehicle tailVehicle = vehicleToAdd.getLane().getTailVehicle();
			position = tailVehicle.getPosition() - vehicle.getVehicleType().getMinHeadway() * vehicleToAdd.getInitSpeed();
		} catch(NoSuchElementException e) {
		}
		vehicle.setPosition(position);
		String name = "vehicle" + count;
		vehicle.setName(name);
		vehicleToAdd.getLane().addVehicle(vehicle);
		return vehicle;
	}
	
}
