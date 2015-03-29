/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.plugin.core;

import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;
import org.tripsim.engine.simulation.SimulationEnvironment;
import org.tripsim.plugin.AbstractPlugin;
import org.tripsim.plugin.api.ICarFollowing;
import org.tripsim.plugin.api.IDriver;
import org.tripsim.plugin.api.IVehicle;
import org.tripsim.util.Pair;

/**
 * 
 * 
 * @author Xuan Shi
 */
abstract class AbstractCarFollowingImpl extends AbstractPlugin implements
		ICarFollowing {
	private static final long serialVersionUID = 1L;

	@Override
	public final void update(SimulationEnvironment environment,
			Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
		CarFollowingEnvironment carFollowingEnvironment = new CarFollowingEnvironment(
				environment, vehicle, stream, web);
		carFollowingEnvironment.update();
	}

	protected class CarFollowingEnvironment extends AbstractVehicleEnvironment {

		CarFollowingEnvironment(SimulationEnvironment environment,
				Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
			super(environment, vehicle, stream, web);
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
			double lookBehindDistance = vehicle.getLookBehindDistance();

			Pair<Double, Vehicle> pair = calculateSpacing(stream.getPath(),
					lookAheadDistance, true);
			double spacing = pair.primary();
			Vehicle leadingVehicle = pair.secondary();
			pair = calculateSpacing(stream.getPath(), lookBehindDistance, false);
			double tailSpacing = pair.primary();
			@SuppressWarnings("unused")
			Vehicle followingVehicle = pair.secondary();

			double accel;
			if (leadingVehicle == null) {
				accel = desiredAccel;
			} else {
				double maxSpeed = vehicle.getMaxSpeed();
				double maxAccel = iVeh
						.getMaxAccel(vehicle.getSpeed(), maxSpeed);
				double maxDecel = iVeh.getMaxDecel(vehicle.getSpeed());
				double desiredDecel = iDrv.getDesiredDecel(vehicle.getSpeed());

				accel = calculateAccel(spacing, tailSpacing,
						vehicle.getReactionTime(), vehicle.getLength(),
						vehicle.getSpeed(), desiredSpeed, maxAccel, maxDecel,
						desiredAccel, desiredDecel, leadingVehicle.getLength(),
						leadingVehicle.getSpeed(), environment.getStepSize());

				if (accel > maxAccel) {
					accel = maxAccel;
				}
				if (accel < maxDecel) {
					accel = maxDecel;
				}
			}

			vehicle.setAcceleration(accel);
		}

	}

	abstract protected double calculateAccel(double spacing,
			double tailSpacing, double reactionTime, double length,
			double speed, double desiredSpeed, double maxAccel,
			double maxDecel, double desiredAccel, double desiredDecel,
			double leadLength, double leadSpeed, double stepSize);
}
