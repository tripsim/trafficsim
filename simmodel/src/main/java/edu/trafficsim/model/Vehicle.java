/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General  License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General  License for more details.
 *
 * You should have received a copy of the GNU Affero General  License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.model;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Vehicle extends Movable, Agent {

	String getVehicleType();

	String getDriverType();

	VehicleClass getVehicleClass();

	CrusingType getCrusingType();

	double getWidth();

	double getLength();

	double getDesiredSpeed();

	double getMaxSpeed();

	double getDesiredHeadway();

	double getPerceptionTime();

	double getReactionTime();

	Link getLink();

	// if on connector
	Node getNode();

	Lane currentLane();

	void currentLane(Lane lane);

	boolean onConnector();

	Link targetLink();

	void targetLink(Link link);

	ConnectionLane preferredConnector();

	void preferredConnector(ConnectionLane lane);

	Node destination();

	Vehicle leadingVehicle();

	Vehicle precedingVehicle();
}
