package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.util.Randoms;
import edu.trafficsim.plugin.IRouting;
import edu.trafficsim.plugin.IVehicleGenerating;
import edu.trafficsim.plugin.PluginManager;

public class DefaultVehicleGenerating implements IVehicleGenerating {

	// Based on arrival rate (possion dist)
	// An alternative should be based on headway (negative exponential dist)
	@Override
	public final List<Vehicle> newVehicles(Od od, SimulationScenario scenario,
			VehicleFactory vehicleFactory) throws TransformException {
		double time = scenario.getTimer().getForwardedTime();
		double stepSize = scenario.getTimer().getStepSize();
		RandomGenerator rng = scenario.getTimer().getRand()
				.getRandomGenerator();
		Random rand = scenario.getTimer().getRand().getRandom();

		// calculate arrival rate
		int vph = od.vph(time);
		double arrivalRate = ((double) vph) / (3600 / stepSize);

		// random num
		int num = Randoms.poission(arrivalRate, rng);

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < num; i++) {

			// create vehicle with random vehicle type and driver type
			VehicleType vtypeToBuild = Randoms.randomElement(
					od.getVehicleComposition(), rand);
			DriverType dtypeToBuild = Randoms.randomElement(
					od.getDriverComposition(), rand);
			if (vtypeToBuild == null || dtypeToBuild == null)
				continue;

			Vehicle vehicle = vehicleFactory.createVehicle(vtypeToBuild,
					dtypeToBuild, scenario);

			// random initial link and lane
			List<Link> links = new ArrayList<Link>(od.getOrigin()
					.getDownstreams());
			Link link = links.get(rand.nextInt(links.size()));
			List<Lane> lanes = Arrays.asList(link.getLanes());
			Lane lane = lanes.get(rand.nextInt(lanes.size()));

			// random initial speed and acceleration
			double speed = Randoms.uniform(5, 30, rng);
			double accel = Randoms.uniform(0, 1, rng);
			vehicle.speed(speed);
			vehicle.acceleration(accel);
			// set vehicle initial position, keep a min headway (gap) from the
			// last
			// vehicle in lane
			double position = 0;
			Vehicle tailVehicle = lane.getTailVehicle();
			if (tailVehicle != null) {
				position = tailVehicle.position() - (3600 / vph / lanes.size())
						* speed;
				position = position > 0 ? 0 : position;
			}
			vehicle.position(position);

			// add vehicle to the current lane
			vehicle.currentLane(lane);
			vehicle.refresh();

			// update routing info
			IRouting routing = PluginManager.getRoutingImpl(scenario
					.getRoutingType(vtypeToBuild));
			Link targetLink = routing.getSucceedingLink(link, vehicle
					.getVehicleType().getVehicleClass(), scenario.getTimer()
					.getForwardedTime(), rand);
			vehicle.targetLink(targetLink);

			// add vehicle to the simulation
			vehicles.add(vehicle);

			// Test
			StringBuffer sb = new StringBuffer();
			sb.append("Time: " + time + "s -- " + "New Vehicle: ");
			sb.append(vehicle.getName());
			sb.append(" || ");
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
