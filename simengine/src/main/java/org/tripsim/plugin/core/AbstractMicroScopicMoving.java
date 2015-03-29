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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Path;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;
import org.tripsim.engine.simulation.SimulationEnvironment;
import org.tripsim.plugin.AbstractPlugin;
import org.tripsim.plugin.api.IMoving;

/**
 * 
 * 
 * @author Xuan Shi
 */
abstract class AbstractMicroScopicMoving extends AbstractPlugin implements
		IMoving {
	private static final long serialVersionUID = 1L;

	protected static final Logger logger = LoggerFactory
			.getLogger(IMoving.class);

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

	protected class MicroscopicMovingEnvironment extends
			AbstractVehicleEnvironment {

		MicroscopicMovingEnvironment(SimulationEnvironment environment,
				Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
			super(environment, vehicle, stream, web);
		}

		protected void updateSpeed() {
			double newSpeed = vehicle.getSpeed()
					+ deltaSpeed(vehicle.getAcceleration(),
							environment.getStepSize());
			if (newSpeed <= 0) {
				logger.warn(
						"Simulation--{}----Time--{}----Negative speed, wrong algorithm!----{}",
						environment.getSimulationName(),
						environment.getForwardedTime(), vehicle);
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
						"Simulation--{}----Time--{}----vehicle reached its destination, or cannot find path to destination!----{}",
						environment.getSimulationName(),
						environment.getForwardedTime(), vehicle);
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
