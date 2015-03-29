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
package org.tripsim.api.model;

import java.io.Serializable;
import java.util.Collection;

public interface VehicleStream extends Serializable {

	Path getPath();

	Path getExitPath(Vehicle vehicle);

	double getPathLength();

	boolean isEmpty();

	/**
	 * check if it is suitable for vehicle to reach destination from this stream
	 * or path
	 * 
	 * @param vehicle
	 * @return if its suitable
	 */
	boolean isViable(Vehicle vehicle);

	/**
	 * check if it is a suitable for vehicle to reach destination as a next
	 * stream or path
	 * 
	 * @param vehicle
	 * @return if its suitable
	 */
	boolean isViableNext(Vehicle vehicle);

	/**
	 * newly generated or added or merged vehicles after last called flush won't
	 * be included
	 * 
	 * @return
	 */
	Collection<Vehicle> getVehicles();

	boolean isHead(Vehicle vehicle);

	boolean isTail(Vehicle vehicle);

	Vehicle getHeadVehicle();

	Vehicle getTailVehicle();

	Vehicle getLeadingVehicle(Vehicle vehicle);

	Vehicle getFollowingVehicle(Vehicle vehicle);

	double getFrontGap(Vehicle vehicle);

	double getRearGap(Vehicle vehicle);

	double getSpacing(Vehicle vehicle);

	double getTailSpacing(Vehicle vehicle);

	double getSpacingfromHead();

	double getSpacingToTail();

	/**
	 * update vehicle to the new position true if added false if not
	 * 
	 * @param vehicle
	 * @param newPosition
	 * @return
	 */
	boolean updateVehiclePosition(Vehicle vehicle, double newPosition);

	boolean moveIn(Vehicle vehicle, Path fromPath);

	boolean mergeIn(Vehicle vehicle);

	Path moveOrMergeOut(Vehicle vehicle);

	Path getMergeLeftPath(Vehicle vehicle);

	Path getMergeRightPath(Vehicle vehicle);

	Collection<Path> getLeftDestPaths();

	Collection<Path> getRightDestPaths();

	void flush();

	void removeInactive(Vehicle vehicle);

}
