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
package edu.trafficsim.engine;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

/**
 * A factory for creating Od objects.
 */
public interface OdFactory {

	OdMatrix createOdMatrix(Sequence seq);

	OdMatrix createOdMatrix(Sequence seq, String name);

	OdMatrix createOdMatrix(Long id, String name);

	Od createOd(Sequence seq, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException;

	Od createOd(Sequence seq, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	Od createOd(Long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	TurnPercentage createTurnPercentage(Sequence seq, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException;

	TurnPercentage createTurnPercentage(Long id, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException;
}
