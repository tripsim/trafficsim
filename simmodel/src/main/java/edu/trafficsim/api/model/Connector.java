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
package edu.trafficsim.api.model;

import edu.trafficsim.api.Environment;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Connector extends Arc, Environment {

	// TODO not to stick it with a node, make it connect to a location anywhere
	// on a lane
	public Node getNode();

	public Lane getFromLane();

	public Lane getToLane();

}
