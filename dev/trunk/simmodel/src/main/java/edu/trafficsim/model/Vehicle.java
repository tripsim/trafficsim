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


/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Vehicle extends Movable, Agent {

	public VehicleType getVehicleType();

	public void setDriverType(VehicleType vehicleType);

	public DriverType getDriverType();

	public void setDriverType(DriverType driverType);

	public Link getLink();

	public Lane currentLane();

	public void currentLane(Lane lane);

	public boolean onConnector();

	public Link targetLink();

	public void targetLink(Link link);

	public ConnectionLane preferredConnector();

	public void preferredConnector(ConnectionLane lane);

	public Node destination();

	public Vehicle leadingVehicle();

	public Vehicle precedingVehicle();

	public double getWidth();

	public double getLength();

	public double getDesiredSpeed();

	public double getMaxSpeed();

	public double getDesiredHeadway();

	public double getReactionTime();
}
