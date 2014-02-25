package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.VehicleFactory.VehicleSpecs;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.core.Randoms;
import edu.trafficsim.plugin.IVehicleGenerating;

public class DefaultVehicleGenerating implements IVehicleGenerating {

	// Based on arrival rate (possion dist)
	// An alternative should be based on headway (negative exponential dist)
	@Override
	public final List<Vehicle> newVehicles(Od od, SimulationScenario scenario,
			VehicleFactory vehicleFactory) throws TransformException {
		double time = scenario.getSimulator().getForwardedTime();
		double stepSize = scenario.getSimulator().getStepSize();
		Random rand = scenario.getSimulator().getRand();

		int vph = od.vph(time);
		double arrivalRate = ((double) vph) / (3600 / stepSize);

		PoissonDistribution dist = new PoissonDistribution(arrivalRate);
		double prob = rand.nextDouble();
		int num = dist.inverseCumulativeProbability(prob);

		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		for (int i = 0; i < num; i++) {

			// random vehicle type and driver type
			VehicleType vtypeToBuild = Randoms.randomElement(
					od.getVehicleTypeComposition(time), rand);
			DriverType dtypeToBuild = Randoms.randomElement(
					od.getDriverTypeComposition(time), rand);

			// random initial speed and acceleration
			double speed = Randoms.uniform(5, 30, rand);
			double accel = 0.2;

			// random initial link and lane
			List<Link> links = new ArrayList<Link>(od.getOrigin()
					.getDownstreams());
			Link link = links.get(rand.nextInt(links.size()));
			List<Lane> lanes = Arrays.asList(link.getLanes());
			Lane lane = lanes.get(rand.nextInt(lanes.size()));

			// generate vehicle from spec
			VehicleSpecs vehicleSpecs = new VehicleSpecs(vtypeToBuild,
					dtypeToBuild, lane, speed, accel);
			Vehicle vehicle = vehicleFactory.createVehicle(vehicleSpecs,
					scenario);
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
