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
package org.tripsim.engine.simulation;

import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;
import org.tripsim.plugin.api.ICarFollowing;
import org.tripsim.plugin.api.IDriver;
import org.tripsim.plugin.api.ILaneChanging;
import org.tripsim.plugin.api.IMoving;
import org.tripsim.plugin.api.IRouting;
import org.tripsim.plugin.api.ISimulating;
import org.tripsim.plugin.api.IVehicle;
import org.tripsim.plugin.api.IVehicleGenerating;

public interface SimulationEnvironment {

	String getSimulationName();

	Network getNetwork();

	OdMatrix getOdMatrix();

	double getStepSize();

	long getForwardedSteps();

	double getForwardedTime();

	long getTotalSteps();

	long getDuration();

	long getSeed();

	double getSd();

	long getAndIncrementVehicleCount();

	Random getRandom();

	RandomGenerator getRandomGenerator();

	ISimulating getSimulating();

	IMoving getMoving(String vehicleType);

	ICarFollowing getCarFollowing(String vehicleType);

	ILaneChanging getLaneChanging(String vehicleType);

	IVehicleGenerating getVehicleGenerating();

	IRouting getRouting(String vehicleType);

	IVehicle getVehicleImpl(String vehicleType);

	IDriver getDriverImpl(String driverType);

	void applyCarFollowing(Vehicle vehicle, VehicleStream stream, VehicleWeb web);

	void applyMoving(Vehicle vehicle, VehicleStream stream, VehicleWeb web);

	void applyLaneChanging(Vehicle vehicle, VehicleStream stream, VehicleWeb web);

	List<Vehicle> generateVehicles(Od od);

	Link getNextLinkInRoute(Vehicle vehicle);

}
