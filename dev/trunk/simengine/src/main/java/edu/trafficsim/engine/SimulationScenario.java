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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.utility.Timer;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class SimulationScenario implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Network network;
	private final OdMatrix odMatrix;
	private final Timer timer;
	private final Sequence seq;

	final String simulatingType = null;
	final String vehicleGeneratingType = null;
	final Map<VehicleType, String> routingTypes = new HashMap<VehicleType, String>();
	final Map<VehicleType, String> movingTypes = new HashMap<VehicleType, String>();
	final Map<VehicleType, String> carFollowingTypes = new HashMap<VehicleType, String>();
	final Map<VehicleType, String> laneChangingTypes = new HashMap<VehicleType, String>();

	public static final SimulationScenario create(Network network,
			OdMatrix odMatrix, Timer timer, Sequence sequence) {
		return new SimulationScenario(network, odMatrix, timer, sequence);
	}

	SimulationScenario(Network network, OdMatrix odMatrix, Timer timer,
			Sequence sequence) {
		this.network = network;
		this.odMatrix = odMatrix;
		this.timer = timer;
		this.seq = sequence;
	}

	public final Network getNetwork() {
		return network;
	}

	public final OdMatrix getOdMatrix() {
		return odMatrix;
	}

	public final Timer getTimer() {
		return timer;
	}

	public final Sequence getSequence() {
		return seq;
	}

	public final String getMovingType(VehicleType vehicleType) {
		return movingTypes.get(vehicleType);
	}

	public final String getRoutingType(VehicleType vehicleType) {
		return routingTypes.get(vehicleType);
	}

	public final String getCarFollowingType(VehicleType vehicleType) {
		return carFollowingTypes.get(vehicleType);
	}

	public final String getLaneChangingType(VehicleType vehicleType) {
		return laneChangingTypes.get(vehicleType);
	}

	public final String getVehicleGeneratingType() {
		return vehicleGeneratingType;
	}

	public final String getSimulatingType() {
		return simulatingType;
	}

}
