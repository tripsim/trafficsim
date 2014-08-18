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
package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Od extends DataContainer {

	Long getId();

	Node getOrigin();

	Node getDestination();

	Node setDestination(Node destination);

	int vph(double time);

	Collection<Double> getJumpTimes();

	Collection<Integer> getVphs();

	void setVphs(double[] times, Integer[] vphs) throws ModelInputException;

	VehicleTypeComposition getVehicleComposition();

	void setVehicleComposition(VehicleTypeComposition composition);

	DriverTypeComposition getDriverComposition();

	void setDriverComposition(DriverTypeComposition composition);

}
