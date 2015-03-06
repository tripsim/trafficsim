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

import edu.trafficsim.api.model.Path;
import edu.trafficsim.api.model.Vehicle;
import edu.trafficsim.api.model.VehicleStream;
import edu.trafficsim.api.model.VehicleWeb;
import edu.trafficsim.engine.simulation.SimulationEnvironment;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.api.ICarFollowing;
import edu.trafficsim.plugin.api.IDriver;
import edu.trafficsim.plugin.api.IVehicle;
import edu.trafficsim.util.Pair;

/**
 * 
 * 
 * @author Xuan Shi
 */
public abstract class AbstractCarFollowingImpl extends AbstractPlugin implements
		ICarFollowing {
	private static final long serialVersionUID = 1L;

	@Override
	public final void update(SimulationEnvironment environment,
			Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
		CarFollowingEnvironment carFollowingEnvironment = new CarFollowingEnvironment(
				environment, vehicle, stream, web);
		carFollowingEnvironment.update();
	}

	private class CarFollowingEnvironment {

		private final SimulationEnvironment environment;
		private final Vehicle vehicle;
		private final VehicleStream stream;
		private final VehicleWeb web;

		CarFollowingEnvironment(SimulationEnvironment environment,
				Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
			this.environment = environment;
			this.vehicle = vehicle;
			this.stream = stream;
			this.web = web;
		}

		public final void update() {
			if (!vehicle.isActive()) {
				return;
			}

			IVehicle iVeh = environment
					.getVehicleImpl(vehicle.getVehicleType());
			IDriver iDrv = environment.getDriverImpl(vehicle.getDriverType());

			double desiredSpeed = vehicle.getDesiredSpeed();
			double desiredAccel = iDrv.getDesiredAccel(vehicle.getSpeed(),
					desiredSpeed);
			double lookAheadDistance = vehicle.getLookAheadDistance();

			Pair<Double, Vehicle> pair = calculateSpacing(lookAheadDistance);
			double spacing = pair.primary();
			Vehicle leadingVehicle = pair.secondary();

			double accel;
			if (leadingVehicle == null) {
				accel = desiredAccel;
			} else {
				double maxSpeed = vehicle.getMaxSpeed();
				double maxAccel = iVeh
						.getMaxAccel(vehicle.getSpeed(), maxSpeed);
				double maxDecel = iVeh.getMaxDecel(vehicle.getSpeed());
				double desiredDecel = iDrv.getDesiredDecel(vehicle.getSpeed());

				accel = calculateAccel(spacing, vehicle.getReactionTime(),
						vehicle.getLength(), vehicle.getSpeed(), desiredSpeed,
						maxAccel, maxDecel, desiredAccel, desiredDecel,
						leadingVehicle.getLength(), leadingVehicle.getSpeed(),
						environment.getStepSize());

				if (accel > maxAccel) {
					accel = maxAccel;
				}
				if (accel < maxDecel) {
					accel = maxDecel;
				}
			}

			vehicle.setAcceleration(accel);
		}

		private Pair<Double, Vehicle> calculateSpacing(double maxSpacing) {
			double spacing = stream.getSpacing(vehicle);
			Vehicle leadingVehicle = stream.getLeadingVehicle(vehicle);
			if (stream.isHead(vehicle) && spacing < maxSpacing) {
				Pair<Double, Vehicle> pair = searchSpacing(
						stream.getExitPath(vehicle), maxSpacing - spacing);
				spacing += pair.primary();
				leadingVehicle = pair.secondary();
			}
			return Pair.create(spacing, leadingVehicle);
		}

		// search the minimum spacing downstream current PATH
		// assuming no lane changing
		private Pair<Double, Vehicle> searchSpacing(Path path, double maxSpacing) {
			if (path == null) {
				return Pair.create(maxSpacing, (Vehicle) null);
			}

			double spacing = maxSpacing;
			Vehicle leadingVehicle = null;
			VehicleStream stream = web.getStream(path);
			if (!stream.isEmpty()) {
				spacing = stream.getSpacingToTail();
				leadingVehicle = stream.getTailVehicle();
			} else {
				for (Path exit : path.getExits()) {
					double h = stream.getPathLength();
					Pair<Double, Vehicle> pair = searchSpacing(exit, maxSpacing
							- h);
					h += pair.primary();
					if (h < spacing) {
						spacing = h;
						leadingVehicle = pair.secondary();
					}
				}
			}
			return Pair.create(spacing, leadingVehicle);
		}

	}

	abstract protected double calculateAccel(double spacing,
			double reactionTime, double length, double speed,
			double desiredSpeed, double maxAccel, double maxDecel,
			double desiredAccel, double desiredDecel, double leadLength,
			double leadSpeed, double stepSize);
}
