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
package org.tripsim.model.vehicle;

import org.tripsim.api.model.Link;
import org.tripsim.api.model.Motion;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleClass;
import org.tripsim.model.core.CrusingType;
import org.tripsim.model.core.MovingObject;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultVehicle extends MovingObject implements Vehicle {

	private static final long serialVersionUID = 1L;

	private String vehicleType;
	private String driverType;

	private VehicleClass vehicleClass;
	private CrusingType crusingType;
	private double width;
	private double length;
	private double maxSpeed;
	private double desiredSpeed;
	private double desiredHeadway;
	private double perceptionTime;
	private double reactionTime;
	private double lookAheadDistance;

	// TODO collection of last motions

	private Node origin = null;
	private Node destination = null;
	private Link currentLink = null;
	private Link nextLink = null;

	DefaultVehicle(long id, long startFrame, VehicleClass vehicleClass,
			String vehicleType, String driverType) {
		super(id, startFrame);
		this.vehicleClass = vehicleClass;
		this.vehicleType = vehicleType;
		this.driverType = driverType;
	}

	// Vehicle Properties
	@Override
	public final String getVehicleType() {
		return vehicleType;
	}

	final void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public final String getDriverType() {
		return driverType;
	}

	final void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}

	void setVehicleClass(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public CrusingType getCrusingType() {
		return crusingType;
	}

	void setCrusingType(CrusingType crusingType) {
		this.crusingType = crusingType;
	}

	@Override
	public final double getWidth() {
		return width;
	}

	final void setWidth(double width) {
		this.width = width;
	}

	@Override
	public final double getLength() {
		return length;
	}

	final void setLength(double length) {
		this.length = length;
	}

	@Override
	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	void setDesiredSpeed(double desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

	@Override
	public double getMaxSpeed() {
		return maxSpeed;
	}

	void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public double getDesiredHeadway() {
		return desiredHeadway;
	}

	void setDesiredHeadway(double desiredHeadway) {
		this.desiredHeadway = desiredHeadway;
	}

	@Override
	public double getPerceptionTime() {
		return perceptionTime;
	}

	void setPerceptionTime(double perceptionTime) {
		this.perceptionTime = perceptionTime;
	}

	@Override
	public double getReactionTime() {
		return reactionTime;
	}

	void setReactionTime(double reactionTime) {
		this.reactionTime = reactionTime;
	}

	@Override
	public double getLookAheadDistance() {
		return lookAheadDistance;
	}

	void setLookAheadDistance(double lookAheadDistance) {
		this.lookAheadDistance = lookAheadDistance;
	}

	@Override
	public Node getOrigin() {
		return origin;
	}

	void setOrigin(Node origin) {
		this.origin = origin;
	}

	@Override
	public final Node getDestination() {
		return destination;
	}

	void setDestination(Node destination) {
		this.destination = destination;
	}

	@Override
	public Link getCurrentLink() {
		return currentLink;
	}

	@Override
	public Link getNextLink() {
		return nextLink;
	}

	@Override
	public void goToNextLinkAndSetNew(Link nextLink) {
		if (this.currentLink == nextLink) {
			throw new IllegalArgumentException("cannot move from "
					+ this.currentLink + " to " + nextLink);
		}
		this.currentLink = this.nextLink;
		this.nextLink = nextLink;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + getId() + ", position=" + getPosition()
				+ ", speed=" + getSpeed() + ", accel=" + getAcceleration()
				+ ", currentLink=" + currentLink + ", nextLink=" + nextLink
				+ "]";
	}

	@Override
	protected void onUpdate(Motion motion) {

	}

}
