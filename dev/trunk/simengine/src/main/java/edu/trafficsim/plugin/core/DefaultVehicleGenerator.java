package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.VehicleFactory.VehicleSpecs;
import edu.trafficsim.model.CarFollowingType;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.LaneChangingType;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.MovingType;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleBehavior;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.core.Randoms;

public class DefaultVehicleGenerator extends AbstractVehicleGenerator {

	public DefaultVehicleGenerator(VehicleFactory vehicleFactory) {
		super(vehicleFactory);
	}

	// Based on arrival rate
	// The other should be based on headway
	@Override
	public final List<Vehicle> getVehicles(Od od, Simulator simulator)
			throws TransformException {
		double time = simulator.getForwardedTime();
		double stepSize = simulator.getStepSize();
		Random rand = simulator.getRand();

		int vph = od.vph(time);
		double arrivalRate = ((double) vph) / (3600 / stepSize);

		PoissonDistribution dist = new PoissonDistribution(arrivalRate);
		double prob = rand.nextDouble();
		int num = dist.inverseCumulativeProbability(prob);

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < num; i++) {
			VehicleType vtypeToBuild = Randoms.randomElement(
					od.getVehicleTypeComposition(time), rand);
			DriverType dtypeToBuild = Randoms.randomElement(
					od.getDriverTypeComposition(time), rand);

			// TODO random vehicle behavior, speed and accel
			MovingType movingType = new MovingType(0, "temp");
			CarFollowingType carfollowingType = new CarFollowingType(0, "temp");
			LaneChangingType laneChangingType = new LaneChangingType(0, "temp");
			VehicleBehavior vehicleBehavior = vehicleFactory.createBehavior(
					"temp", movingType, carfollowingType, laneChangingType);
			double speed = Randoms.uniform(5, 30, rand);
			double accel = 0.2;

			// TODO setup routing
			List<Link> links = new ArrayList<Link>(od.getOrigin()
					.getDownstreams());
			Link link = links.get(rand.nextInt(links.size()));
			List<Lane> lanes = Arrays.asList(link.getLanes());
			Lane lane = lanes.get(rand.nextInt(lanes.size()));

			VehicleSpecs vehicleSpecs = new VehicleSpecs(vtypeToBuild,
					dtypeToBuild, vehicleBehavior, lane, speed, accel);
			Vehicle vehicle = vehicleFactory.createVehicle(vehicleSpecs,
					simulator);
			vehicles.add(vehicle);

			// Test
			StringBuffer sb = new StringBuffer();
			sb.append("Time: " + time + "s -- " + "New Vehicle: ");
			sb.append("VehicleType -> ");
			sb.append(vtypeToBuild.getName());
			sb.append(" || ");
			sb.append("DriverType -> ");
			sb.append(dtypeToBuild.getName());
			System.out.println(sb.toString());
		}

		return vehicles;
	}

}
