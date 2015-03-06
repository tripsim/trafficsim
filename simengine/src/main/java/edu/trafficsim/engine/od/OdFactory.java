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
package edu.trafficsim.engine.od;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.api.model.TurnPercentage;

/**
 * A factory for creating Od objects.
 */
public interface OdFactory {

	OdMatrix createOdMatrix(String networkName);

	OdMatrix createOdMatrix(String name, String networkName);

	Od createOd(Long id, Long originNodeId, Long destinationNodeId,
			String vehicleTypeComposition, String driverTypeComposition,
			double[] times, Integer[] vphs);

	TurnPercentage createTurnPercentage(String name, Link upstream,
			Link[] downstreams, double[] percentages);
}