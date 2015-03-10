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
package org.tripsim.engine.type;

import org.tripsim.model.core.Aggressiveness;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DriverType extends Type {

	double perceptionTime = 2;
	double reactionTime = 2;

	double desiredHeadway = 2;
	double desiredSpeed = 25;

	private Aggressiveness drivingAggressiveness;
	private Aggressiveness routeAggressiveness;

	DriverType(String name) {
		super(name);
	}

	public double getPerceptionTime() {
		return perceptionTime;
	}

	public void setPerceptionTime(double perceptionTime) {
		this.perceptionTime = perceptionTime;
	}

	public double getReactionTime() {
		return reactionTime;
	}

	public void setReactionTime(double reactionTime) {
		this.reactionTime = reactionTime;
	}

	public double getDesiredHeadway() {
		return desiredHeadway;
	}

	public void setDesiredHeadway(double desiredHeadway) {
		this.desiredHeadway = desiredHeadway;
	}

	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	public void setDesiredSpeed(double desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

	public Aggressiveness getDrivingAggressiveness() {
		return drivingAggressiveness;
	}

	public void setDrivingAggressiveness(Aggressiveness drivingAggressiveness) {
		this.drivingAggressiveness = drivingAggressiveness;
	}

	public Aggressiveness getRouteAggressiveness() {
		return routeAggressiveness;
	}

	public void setRouteAggressiveness(Aggressiveness routeAggressiveness) {
		this.routeAggressiveness = routeAggressiveness;
	}

}
