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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Path;
import edu.trafficsim.api.model.Vehicle;
import edu.trafficsim.api.model.VehicleStream;
import edu.trafficsim.api.model.VehicleWeb;
import edu.trafficsim.engine.simulation.SimulationEnvironment;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.api.IMoving;

/**
 * 
 * 
 * @author Xuan Shi
 */
public abstract class AbstractMicroScopicMoving extends AbstractPlugin
		implements IMoving {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractMicroScopicMoving.class);

	@Override
	public final void update(SimulationEnvironment environment,
			Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
		if (!vehicle.isActive()) {
			return;
		}

		MicroscopicMovingEnvironment movingEnvironment = new MicroscopicMovingEnvironment(
				environment, vehicle, stream, web);
		movingEnvironment.updateSpeed();
		movingEnvironment.updatePosition();
	}

	protected class MicroscopicMovingEnvironment {

		private final SimulationEnvironment environment;
		private final Vehicle vehicle;
		private final VehicleStream stream;
		private final VehicleWeb web;

		MicroscopicMovingEnvironment(SimulationEnvironment environment,
				Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
			this.environment = environment;
			this.vehicle = vehicle;
			this.stream = stream;
			this.web = web;
		}

		protected void updateSpeed() {
			double newSpeed = vehicle.getSpeed()
					+ deltaSpeed(vehicle.getAcceleration(),
							environment.getStepSize());
			if (newSpeed <= 0) {
				logger.error("Negative speed, wrong algorithm!");
				newSpeed = vehicle.getSpeed();
			}
			vehicle.setSpeed(newSpeed);
		}

		protected void updatePosition() {
			double oldPosition = vehicle.getPosition();
			double newPosition = oldPosition
					+ deltaPosition(vehicle.getDirection(), vehicle.getSpeed(),
							environment.getStepSize());

			if (!stream.updateVehiclePosition(vehicle, newPosition)) {
				moveToNextStream(stream);
			}
		}

		private void moveToNextStream(VehicleStream stream) {
			Path nextPath = stream.getExitPath(vehicle);
			if (nextPath == null) {
				logger.info(
						"vehicle {} reached its destination, or cannot find path to destination!",
						vehicle.getId());
				vehicle.deactivate();
				return;
			}
			VehicleStream nextStream = web.getStream(nextPath);
			Link nextLink = environment.getNextLinkInRoute(vehicle);
			vehicle.goToNextLinkAndSetNew(nextLink);
			if (!nextStream.moveIn(vehicle, stream.getPath())) {
				moveToNextStream(nextStream);
			}
		}
	}

	protected abstract double deltaPosition(double direction, double speed,
			double stepSize);

	protected abstract double deltaLateralOffset(double direction,
			double speed, double stepSize);

	protected abstract double deltaSpeed(double accel, double stepSize);

}
