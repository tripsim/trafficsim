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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.TurnPercentage;
import org.tripsim.api.model.TypesComposition;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.model.demand.DefaultOd;
import org.tripsim.model.demand.DefaultOdMatrix;
import org.tripsim.model.demand.DefaultTurnPercentage;

/**
 * A factory for creating DefaultOd objects.
 * 
 * @author Xuan Shi
 */
@Component("default-od-factory")
public class DefaultOdFactory implements OdFactory {

	private static final String DEFAULT_NAME = "Default";

	@Autowired
	TypesManager typesManager;

	@Override
	public OdMatrix createOdMatrix(String networkName) {
		return createOdMatrix(DEFAULT_NAME, networkName);
	}

	@Override
	public OdMatrix createOdMatrix(String name, String networkName) {
		return new DefaultOdMatrix(name, networkName);
	}

	@Override
	public Od createOd(Long id, Long originNodeId, Long destinationNodeId,
			String vehicleTypeComposition, String driverTypeComposition,
			double[] times, Integer[] vphs) {
		TypesComposition vtCompo = typesManager
				.getVehicleTypeComposition(vehicleTypeComposition);
		TypesComposition dtCompo = typesManager
				.getDriverTypeComposition(driverTypeComposition);
		return new DefaultOd(id, originNodeId, destinationNodeId, vtCompo,
				dtCompo, times, vphs);
	}

	@Override
	public TurnPercentage createTurnPercentage(String name, Link upstream,
			Link[] downstreams, double[] percentages) {
		return new DefaultTurnPercentage(name, upstream, downstreams,
				percentages);
	}

}
