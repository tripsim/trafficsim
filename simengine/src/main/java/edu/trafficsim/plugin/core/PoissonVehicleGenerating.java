/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.trafficsim.engine.simulation.Tracker;
import edu.trafficsim.engine.vehicle.VehicleFactory;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.util.Randoms;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IRouting;
import edu.trafficsim.plugin.IVehicleGenerating;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Poisson Vehicle-generating")
public class PoissonVehicleGenerating extends AbstractPlugin implements
		IVehicleGenerating {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(PoissonVehicleGenerating.class);

	@Autowired
	VehicleFactory vehicleFactory;

	/*
	 * (non-Javadoc) Based on arrival rate (possion dist)An alternative should
	 * be based on headway (negative exponential dist)
	 */
	@Override
	public final List<Vehicle> newVehicles(Od od, Tracker tracker) {
		double time = tracker.getForwardedTime();
		double stepSize = tracker.getStepSize();
		RandomGenerator rng = tracker.getRand().getRandomGenerator();
		Random rand = tracker.getRand().getRandom();

		// calculate arrival rate
		int vph = od.vph(time);
		if (vph <= 0)
			return Collections.emptyList();
		double arrivalRate = ((double) vph) / (3600 / stepSize);

		// random num
		int num = Randoms.poission(arrivalRate, rng);

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < num; i++) {

			// create vehicle with random vehicle type and driver type
			String vtypeToBuild = Randoms.randomElement(
					od.getVehicleComposition(), rand);
			String dtypeToBuild = Randoms.randomElement(
					od.getDriverComposition(), rand);
			if (vtypeToBuild == null || dtypeToBuild == null)
				continue;

			Vehicle vehicle = vehicleFactory.createVehicle(vtypeToBuild,
					dtypeToBuild, tracker);

			// random initial link and lane
			List<Link> links = new ArrayList<Link>(od.getOrigin()
					.getDownstreams());
			if (links.isEmpty())
				continue;
			Link link = links.get(rand.nextInt(links.size()));
			List<Lane> lanes = Arrays.asList(link.getLanes());
			if (lanes.isEmpty())
				continue;
			Lane lane = lanes.get(rand.nextInt(lanes.size()));

			// random initial speed and acceleration
			double speed = Randoms.uniform(5, 30, rng);
			double accel = Randoms.uniform(0, 1, rng);
			vehicle.speed(speed);
			vehicle.acceleration(accel);
			// set vehicle initial position, keep a min headway (gap) from the
			// last vehicle in lane
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
			try {
				vehicle.refresh();
			} catch (TransformException e) {
				logger.error(
						"Skipped generating vehicle for Transformation Error during vehicle generation on lane {}",
						lane);
				continue;
			}

			// update routing info
			IRouting routing = pluginManager.getRoutingImpl(tracker
					.getRoutingType(vtypeToBuild));
			Link targetLink = routing
					.getSucceedingLink(link, vehicle.getVehicleClass(),
							tracker.getForwardedTime(), rand);
			vehicle.targetLink(targetLink);

			// add vehicle to the simulation
			vehicles.add(vehicle);

			// Test
			StringBuffer sb = new StringBuffer();
			sb.append("Time: " + time + "s -- " + "New Vehicle: ");
			sb.append(vehicle.getName());
			sb.append(" || ");
			sb.append("VehicleType -> ");
			sb.append(vtypeToBuild);
			sb.append(" || ");
			sb.append("DriverType -> ");
			sb.append(dtypeToBuild);
			logger.info(sb.toString());
		}

		return vehicles;
	}

	@Override
	public String getName() {
		return "Poisson Vehicle Generation";
	}
}
