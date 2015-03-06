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

import java.util.Random;

import org.springframework.stereotype.Component;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.api.model.TurnPercentage;
import edu.trafficsim.api.model.VehicleClass;
import edu.trafficsim.engine.simulation.SimulationEnvironment;
import edu.trafficsim.util.Randoms;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Turn-percentage Routing")
public class TurnPercentageRouting extends AbstractRouting {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Turn Percentage Routing";
	}

	@Override
	public Link searchNextLink(SimulationEnvironment environment,
			Link currentLink, Node Destination, VehicleClass vehicleClass) {
		OdMatrix odMatrix = environment.getOdMatrix();
		double forwardedTime = environment.getForwardedTime();
		Random random = environment.getRandom();

		TurnPercentage turnPercentage = odMatrix.getTurnPercentage(currentLink,
				vehicleClass, forwardedTime);
		return turnPercentage != null ? Randoms.randomElement(turnPercentage,
				random) : randomSucceedingLink(environment,
				currentLink.getEndNode());
	}
}
