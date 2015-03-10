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
package org.tripsim.engine.od;

import org.tripsim.api.model.Link;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.TurnPercentage;

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