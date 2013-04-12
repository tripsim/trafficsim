package edu.trafficsim.factory;

import java.util.NoSuchElementException;

import edu.trafficsim.model.demand.VehicleToAdd;
import edu.trafficsim.model.roadusers.Vehicle;

public class VehicleFactory extends AbstractFactory {
	
	private static VehicleFactory factory;
	
	private static int count = 0;
	
	private VehicleFactory() {
	}
	
	public static VehicleFactory getInstance() {
		if (factory == null)
			factory = new VehicleFactory();
		return factory;
	}
	
	// TODO complete the creation
	public Vehicle createVehicle(VehicleToAdd vehicleToAdd, double startTime, double stepSize) {
		count++;
		Vehicle vehicle = new Vehicle(vehicleToAdd.getVehicleType(), vehicleToAdd.getDriverType(), startTime);
		vehicle.speed(vehicleToAdd.getInitSpeed());
		vehicle.acceleration(vehicleToAdd.getInitAcceleration());
		double position = 0;
		try {
			Vehicle tailVehicle = vehicleToAdd.getLane().getTailVehicle();
			position = tailVehicle.position() - vehicle.getVehicleType().getMinHeadway() * vehicleToAdd.getInitSpeed();
			position = position > 0 ? 0 : position;
		} catch(NoSuchElementException e) {
		}
		vehicle.position(position);
		String name = "vehicle" + count;
		vehicle.setName(name);
		vehicleToAdd.getLane().add(vehicle);
		return vehicle;
	}
	
}
