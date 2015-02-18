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

import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.TypesComposition;
import edu.trafficsim.model.core.ModelInputException;
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
	public OdMatrix createOdMatrix(Long id, String networkName) {
		return createOdMatrix(id, DEFAULT_NAME, networkName);
	}

	@Override
	public OdMatrix createOdMatrix(Long id, String name, String networkName) {
		return new DefaultOdMatrix(id, name, networkName);
	}

	@Override
	public Od createOd(Long id, String name, Long originNodeId,
			Long destinationNodeId, String vehicleTypeComposition,
			String driverTypeComposition, double[] times, Integer[] vphs)
			throws ModelInputException {
		TypesComposition vtCompo = typesManager
				.getVehicleTypeComposition(vehicleTypeComposition);
		TypesComposition dtCompo = typesManager
				.getDriverTypeComposition(driverTypeComposition);
		return new DefaultOd(id, name, originNodeId, destinationNodeId,
				vtCompo, dtCompo, times, vphs);
	}

	@Override
	public TurnPercentage createTurnPercentage(String name, Link upstream,
			Link[] downstreams, double[] percentages)
			throws ModelInputException {
		return new DefaultTurnPercentage(name, upstream, downstreams,
				percentages);
	}

}
