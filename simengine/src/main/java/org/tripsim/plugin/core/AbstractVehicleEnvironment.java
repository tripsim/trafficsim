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
package org.tripsim.plugin.core;

import java.util.Collection;

import org.tripsim.api.model.Path;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;
import org.tripsim.engine.simulation.SimulationEnvironment;
import org.tripsim.util.Pair;

abstract class AbstractVehicleEnvironment {

	protected final SimulationEnvironment environment;
	protected final Vehicle vehicle;
	protected final VehicleStream stream;
	protected final VehicleWeb web;

	AbstractVehicleEnvironment(SimulationEnvironment environment,
			Vehicle vehicle, VehicleStream stream, VehicleWeb web) {
		this.environment = environment;
		this.vehicle = vehicle;
		this.stream = stream;
		this.web = web;
	}

	protected Pair<Double, Vehicle> calculateSpacing(Path path,
			double maxSpacing, boolean ahead) {
		VehicleStream stream = web.getStream(path);
		double spacing = ahead ? stream.getSpacing(vehicle) : stream
				.getTailSpacing(vehicle);
		Vehicle adjacentVehicle = ahead ? stream.getLeadingVehicle(vehicle)
				: stream.getFollowingVehicle(vehicle);
		if (spacing < maxSpacing) {
			if ((ahead && stream.isHead(vehicle))
					|| (!ahead && stream.isTail(vehicle))) {
				Collection<? extends Path> nextPaths = ahead ? stream.getPath()
						.getExits() : stream.getPath().getEntrances();
				Pair<Double, Vehicle> pair = searchSpacing(nextPaths,
						maxSpacing - spacing, ahead);
				spacing += pair.primary();
				adjacentVehicle = pair.secondary();
			}
		}
		return Pair.create(spacing, adjacentVehicle);
	}

	private Pair<Double, Vehicle> searchSpacing(
			Collection<? extends Path> paths, double maxSpacing, boolean ahead) {
		if (paths.isEmpty() || maxSpacing <= 0) {
			return Pair.create(maxSpacing, (Vehicle) null);
		}

		double spacing = maxSpacing;
		Vehicle adjacentVehicle = null;
		for (Path path : paths) {
			Pair<Double, Vehicle> pair = searchSpacing(path, maxSpacing, ahead);
			double h = pair.primary();
			if (h < spacing) {
				spacing = h;
				adjacentVehicle = pair.secondary();
			}
		}
		return Pair.create(spacing, adjacentVehicle);
	}

	// search the minimum spacing downstream current PATH
	// assuming no lane changing
	private Pair<Double, Vehicle> searchSpacing(Path path, double maxSpacing,
			boolean ahead) {
		if (path == null) {
			return Pair.create(maxSpacing, (Vehicle) null);
		}

		double spacing = maxSpacing;
		Vehicle adjacentVehcie = null;
		VehicleStream stream = web.getStream(path);
		if (!stream.isEmpty()) {
			spacing = ahead ? stream.getSpacingToTail() : stream
					.getSpacingfromHead();
			adjacentVehcie = ahead ? stream.getTailVehicle() : stream
					.getHeadVehicle();
		} else {
			double h = stream.getPathLength();
			Pair<Double, Vehicle> pair = searchSpacing(path.getExits(),
					maxSpacing - h, ahead);
			h += pair.primary();
			if (h < spacing) {
				spacing = h;
				adjacentVehcie = pair.secondary();
			}
		}
		return Pair.create(spacing, adjacentVehcie);
	}

}
