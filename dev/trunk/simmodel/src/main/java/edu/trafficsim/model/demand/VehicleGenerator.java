package edu.trafficsim.model.demand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.distribution.PoissonDistribution;

import edu.trafficsim.model.core.AbstractProportion;
import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class VehicleGenerator extends BaseEntity<VehicleGenerator> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	private final Map<VehicleClass, VehicleTypeProportion> vehicleTypeProportions = new HashMap<VehicleClass, VehicleTypeProportion>();
	private final DriverTypeProportion driverTypeProportion = new DriverTypeProportion();
	
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
	
	public void addDriverType(DriverType driverType, double proportion) {
		driverTypeProportion.put(driverType, proportion);
	}
	
	// HACK for the demo
	// TODO make it a plugin
	// Based on arrival rate
	// The other should be based on headway
	public List<VehicleToAdd> getVehicleToAdd(Origin origin, double time, double stepSize, Random rand){
		int vph = origin.getVph(time);
		double arrivalRate = ((double) vph) / (3600 / stepSize);
		
		PoissonDistribution dist = new PoissonDistribution(arrivalRate);
		double prob = rand.nextDouble();
		int num = dist.inverseCumulativeProbability(prob);
		
		List<VehicleToAdd> vehicles = new ArrayList<VehicleToAdd>();
		
		for (int i = 0; i < num; i++) {
			Destination destToGo = randomElement(origin.getFlow(), time, rand.nextDouble());
			VehicleClass vclassToBuild = randomElement(origin.getVehicleClassProportion(destToGo, time), rand.nextDouble());
			VehicleType vtypeToBuild = randomElement(vehicleTypeProportions.get(vclassToBuild), rand.nextDouble());
			DriverType dtypeToBuild = randomElement(driverTypeProportion, rand.nextDouble());
			
			// TODO random speed and accel
			double speed = 10;
			double accel = 1;
			
			// TODO setup routing 
			List<Link> links = new ArrayList<Link>(origin.getNode().getOutLinks());
			Link link = links.get(rand.nextInt(links.size()));
			List<Lane> lanes = new ArrayList<Lane>(link.getLanes());
			Lane lane = lanes.get(rand.nextInt(links.size()));
			
			VehicleToAdd vehicle = new VehicleToAdd(vtypeToBuild, dtypeToBuild, lane, speed, accel);
			vehicles.add(vehicle);
		}
		
		return vehicles;
	}
	
	public static <T> T randomElement(AbstractProportion<T> proportion, double threshold) {
		T key = null;
		for (T otherkey : proportion.keys()) {
			threshold -= proportion.getProportion(otherkey);
			if (threshold <= 0 )
				break;
			else
				key = otherkey;
		}
		return key;
	}
	
	public static Destination randomElement(Flow flow, double time, double threshold) {
		Destination destToGo = null;
		int vph = flow.getVph(time);
		for (Destination destination : flow.getDestinations()) {
			threshold -= flow.getVph(destination, time) / vph;
			if (threshold < 0)
				break;
			else
				destToGo = destination;
		}
		return destToGo;
	}
	
	
}
