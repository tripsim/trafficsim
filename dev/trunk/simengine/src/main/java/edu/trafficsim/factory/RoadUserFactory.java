package edu.trafficsim.factory;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.Vehicle;
import edu.trafficsim.model.roadusers.VehicleType;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class RoadUserFactory extends AbstractFactory {
	
	private static RoadUserFactory factory;
	
	// TODO support one vehicleClass to more vehicleTypes
	private Map<VehicleClass, VehicleType> vehicleTypes;
	private DriverType driverType;
	
	private RoadUserFactory() {
		vehicleTypes = new HashMap<VehicleClass, VehicleType>();
		driverType = new DriverType();
	}
	
	public static RoadUserFactory getInstance() {
		if (factory == null)
			factory = new RoadUserFactory();
		return factory;
	}
	
	public void addVehiclType(VehicleClass vehicleClass, VehicleType vehicleType) {
		vehicleTypes.put(vehicleClass, vehicleType);
	}
	
	public Vehicle createVehicle(VehicleClass vehicleClass, double resolution) {
		return new Vehicle(vehicleTypes.get(vehicleClass), driverType, resolution);
	}
	
}
