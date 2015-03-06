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

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.VehicleClass;
import edu.trafficsim.engine.simulation.SimulationEnvironment;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.api.IRouting;
import edu.trafficsim.util.Randoms;

/**
 * 
 * 
 * @author Xuan Shi
 */
public abstract class AbstractRouting extends AbstractPlugin implements
		IRouting {

	private static final long serialVersionUID = 1L;

	@Override
	public Link searchNextLink(SimulationEnvironment environment, Node current,
			Node destination) {
		return randomSucceedingLink(environment, current);
	}

	@Override
	public Link searchNextLink(SimulationEnvironment environment,
			Link currentLink, Node destination, VehicleClass vehicleClass) {
		return randomSucceedingLink(environment, currentLink.getEndNode());
	}

	protected Link randomSucceedingLink(SimulationEnvironment environment,
			Node current) {
		return Randoms.randomElement(current.getDownstreams(),
				environment.getRandom());
	}

}
