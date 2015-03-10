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
package org.tripsim.plugin.api;

import java.util.Set;

public interface PluginManager {

	static final String DEFAULT_SIMULATING = "Micro Scopic Simulating";
	static final String DEFAULT_GENERATING = "Poisson Vehicle-generating";
	static final String DEFAULT_MOVING = "Simple Micro Scopic Moving";
	static final String DEFAULT_CAR_FOLLOWING = "Pipes Car-following";
	static final String DEFAULT_LANE_CHANGING = "Simple Lane-changing";
	static final String DEFAULT_ROUTING = "Random Routing";
	static final String DEFAULT_VEHICLE_IMPL = "Default Vehicle";
	static final String DEFAULT_DRIVER_IMPL = "Default Driver";

	ISimulating getSimulatingImpl(String name);

	Set<String> getSimulatings();

	IMoving getMovingImpl(String name);

	Set<String> getMovings();

	ICarFollowing getCarFollowingImpl(String name);

	Set<String> getCarFollowings();

	ILaneChanging getLaneChangingImpl(String name);

	Set<String> getLaneChangings();

	IVehicleGenerating getVehicleGeneratingImpl(String name);

	Set<String> getVehicleGeneratings();

	IRouting getRoutingImpl(String name);

	Set<String> getRoutings();

	IVehicle getVehicleImpl(String name);

	Set<String> getVehicleImpls();

	IDriver getDriverImpl(String name);

	Set<String> getDriverImpls();
}
