package edu.trafficsim.model.demand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.Origin;
import edu.trafficsim.model.roadusers.VehicleType;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class VehicleGenerator extends BaseEntity<VehicleGenerator> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	private final Map<VehicleClass, VehicleTypeProportion> vehicleTypeProportions = new HashMap<VehicleClass, VehicleTypeProportion>();
	
	public VehicleGenerator() { }

	public void addVehicleType(VehicleType vehicleType, double proportion) throws ModelInputException {
		VehicleTypeProportion vehicleTypeProportion = vehicleTypeProportions.get(vehicleType.getVehicleClass());
		if (vehicleTypeProportion == null) {
			vehicleTypeProportion = new VehicleTypeProportion(vehicleType.getVehicleClass());
			vehicleTypeProportions.put(vehicleType.getVehicleClass(), vehicleTypeProportion);
		}
		if (!vehicleType.getVehicleClass().equals(vehicleTypeProportion.getVehicleClass()))
			throw new ModelInputException("VehicleType Not Match");
		vehicleTypeProportion.put(vehicleType, proportion);
	}
	
	// HACK for the demo
	// TODO make it a plugin
	// Based on arrival rate
	// The other should be based on headway
	public VehicleToBuild getVehicleToBuild(Origin origin, double time, Random rand){
//		int vph = origin.getVph(vehicleClass, time);
		
		return null;
	}
	
	
}
