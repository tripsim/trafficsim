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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.trafficsim.engine.simulation.Tracker;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.util.Randoms;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IMoving;
import edu.trafficsim.plugin.IRouting;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Microscopic Moving")
public class DefaultMoving extends AbstractPlugin implements IMoving {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultMoving.class);

	@Override
	public final void update(Vehicle vehicle, OdMatrix odMatrix, Tracker tracker) {
		if (!vehicle.active())
			return;
		updatePosition(vehicle, odMatrix, tracker);
		if (vehicle.active()) {
			try {
				vehicle.refresh();
			} catch (ModelInputException e) {
				logger.error(
						"Deactivate vehicle '{}' due to failed refresh in moving vehicle!",
						vehicle.getId());
				vehicle.deactivate();
			}
		}
	}

	protected void updatePosition(Vehicle vehicle, OdMatrix odMatrix,
			Tracker tracker) {
		double stepSize = tracker.getStepSize();

		// calculate new speed and new position
		double newSpeed = vehicle.speed() + stepSize * vehicle.acceleration();
		if (newSpeed <= 0) {
			logger.error("Negative speed, wrong algorithm!");
			newSpeed = 0;
		}
		double newPosition = vehicle.position() + stepSize * newSpeed;
		// set new speed
		vehicle.speed(newSpeed);
		// set new position
		if (vehicle.getSubsegment().getLength() - newPosition < 0) {
			vehicle.position(newPosition - vehicle.currentLane().getLength());
			convey(vehicle, odMatrix, tracker);
		} else {
			vehicle.position(newPosition);
		}

	}

	protected void convey(Vehicle vehicle, OdMatrix odMatrix, Tracker tracker) {
		if (vehicle.targetLink() == null) {
			vehicle.currentLane(null);
			vehicle.deactivate();
			return;
		}

		IRouting routing = pluginManager.getRoutingImpl(tracker
				.getRoutingType(vehicle.getVehicleType()));

		logger.debug("------- Debug Convey --------");
		if (vehicle.onConnector()) {
			Link link = routing.getSucceedingLink(odMatrix, vehicle.getLink(),
					vehicle.getVehicleClass(), tracker.getForwardedTime(),
					tracker.getRand().getRandom());
			vehicle.currentLane(((ConnectionLane) vehicle.currentLane())
					.getToLane());
			vehicle.targetLink(link);
		} else {
			Collection<ConnectionLane> connectors = vehicle.currentLane()
					.getToConnectors();
			if (connectors.contains(vehicle.preferredConnector())) {
				vehicle.currentLane(vehicle.preferredConnector());
			} else if (!connectors.isEmpty()) {
				vehicle.currentLane(Randoms.randomElement(connectors, tracker
						.getRand().getRandom()));
			} else {
				vehicle.deactivate();
				return;
			}
		}
		if (vehicle.currentLane().getLength() - vehicle.position() < 0) {
			vehicle.position(vehicle.position()
					- vehicle.currentLane().getLength());
			convey(vehicle, odMatrix, tracker);
		}
	}
}
