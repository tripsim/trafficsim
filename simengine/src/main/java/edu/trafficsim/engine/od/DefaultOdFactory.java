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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.api.model.TurnPercentage;
import edu.trafficsim.api.model.TypesComposition;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.demand.DefaultOd;
import edu.trafficsim.model.demand.DefaultOdMatrix;
import edu.trafficsim.model.demand.DefaultTurnPercentage;

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
