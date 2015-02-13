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

import java.util.Collection;

import edu.trafficsim.engine.simulation.Tracker;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.ICarFollowing;
import edu.trafficsim.plugin.IDriver;
import edu.trafficsim.plugin.IVehicle;

/**
 * 
 * 
 * @author Xuan Shi
 */
public abstract class AbstractCarFollowingImpl extends AbstractPlugin implements
		ICarFollowing {
	private static final long serialVersionUID = 1L;

	private double impactingDistance = 50d;

	private double spacing(Vehicle vehicle) {
		if (vehicle.leadingVehicle() != null)
			return vehicle.leadingVehicle().position() - vehicle.position();

		double spacing = vehicle.currentLane().getLength() - vehicle.position();
		spacing = searchSpacing(spacing, vehicle.currentLane()
				.getToConnectors());
		return spacing;
	}

	// search the maximum possible spacing downstream current LANE
	private double searchSpacing(double spacing,
			Collection<ConnectionLane> connectors) {
		double h = 0;
		for (ConnectionLane connector : connectors) {
			if (connector.getTailVehicle() != null) {
				h = h > connector.getTailVehicle().position() ? h : connector
						.getTailVehicle().position();
			} else if (connector.getToLane().getTailVehicle() != null) {
				h = h > connector.getToLane().getTailVehicle().position() ? h
						: connector.getToLane().getTailVehicle().position();
			} else {
				double nh = connector.getLength()
						+ connector.getToLane().getLength();
				if (spacing + nh < impactingDistance)
					nh = searchSpacing(spacing + nh, connector.getToLane()
							.getToConnectors());
				else
					return spacing + nh;
				h = h > nh ? h : nh;
			}
		}
		return h + spacing;
	}

	@Override
	public final void update(Vehicle vehicle, Tracker tracker) {
		if (!vehicle.active())
			return;
		Vehicle leading = vehicle.leadingVehicle();

		IVehicle iVeh = pluginManager.getVehicleImpl(tracker
				.getVehicleImplType(vehicle.getVehicleType()));
		IDriver iDrv = pluginManager.getDriverImpl(tracker
				.getDriverImplType(vehicle.getDriverType()));
		double desiredSpeed = vehicle.getDesiredSpeed();
		double desiredAccel = iDrv.getDesiredAccel(vehicle.speed(),
				desiredSpeed);

		double accel;
		if (leading == null) {
			accel = desiredAccel;
		} else {
			double maxSpeed = vehicle.getMaxSpeed();
			double maxAccel = iVeh.getMaxAccel(vehicle.speed(), maxSpeed);
			double maxDecel = iVeh.getMaxDecel(vehicle.speed());
			double desiredDecel = iDrv.getDesiredDecel(vehicle.speed());

			accel = calculateAccel(spacing(vehicle), vehicle.getReactionTime(),
					vehicle.getLength(), vehicle.speed(), desiredSpeed,
					maxAccel, maxDecel, desiredAccel, desiredDecel,
					leading.getLength(), leading.speed(), tracker.getStepSize());
		}

		vehicle.acceleration(accel);
	}

	abstract protected double calculateAccel(double spacing,
			double reactionTime, double length, double speed,
			double desiredSpeed, double maxAccel, double maxDecel,
			double desiredAccel, double desiredDecel, double leadLength,
			double leadSpeed, double stepSize);
}
