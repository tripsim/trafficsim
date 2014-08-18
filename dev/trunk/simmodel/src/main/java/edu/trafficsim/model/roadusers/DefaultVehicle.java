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
package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Subsegment;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.core.MovingObject;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultVehicle extends MovingObject<DefaultVehicle> implements
		Vehicle {

	private static final long serialVersionUID = 1L;

	private VehicleType vehicleType;
	private DriverType driverType;

	private Node destination = null;
	private Link targetLink = null;
	private ConnectionLane preferredConnector = null;
	private Lane currentLane = null;

	private double width;
	private double length;
	// private double height;
	private double maxSpeed;
	private double desiredSpeed;
	private double desiredHeadway;
	private double reactionTime;

	public DefaultVehicle(long id, String name, int startFrame,
			VehicleType vehicleType, DriverType driverType) {
		this(id, name, startFrame, vehicleType, driverType, vehicleType
				.getWidth(), vehicleType.getLength(),
				vehicleType.getMaxSpeed(), driverType.getDesiredSpeed(),
				driverType.getDesiredHeadway(), driverType.getReactionTime());
	}

	public DefaultVehicle(long id, String name, int startFrame,
			VehicleType vehicleType, DriverType driverType, double width,
			double length, double maxSpeed, double desiredSpeed,
			double desiredHeadway, double reactionTime) {
		super(id, name, startFrame);
		this.vehicleType = vehicleType;
		this.driverType = driverType;
		this.width = width;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.desiredSpeed = desiredSpeed;
		this.desiredHeadway = desiredHeadway;
	}

	@Override
	public Segment getSegment() {
		return currentLane != null ? currentLane.getSegment() : null;
	}

	@Override
	public Subsegment getSubsegment() {
		return currentLane;
	}

	@Override
	public final VehicleType getVehicleType() {
		return vehicleType;
	}

	@Override
	public final void setDriverType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public final DriverType getDriverType() {
		return driverType;
	}

	@Override
	public final void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	@Override
	public final Link getLink() {
		return (Link) getSegment();
	}

	@Override
	public final Lane currentLane() {
		return currentLane;
	}

	@Override
	public final void currentLane(Lane lane) {
		if (currentLane != null)
			currentLane.remove(this);
		if (lane != null)
			lane.add(this);
		this.currentLane = lane;
	}

	@Override
	public final boolean onConnector() {
		return currentLane == null ? false
				: currentLane instanceof ConnectionLane;
	}

	@Override
	public final Link targetLink() {
		return targetLink;
	}

	public void targetLink(Link link) {
		this.targetLink = link;
	}

	@Override
	public final Node destination() {
		return destination;
	}

	@Override
	public final Vehicle leadingVehicle() {
		return currentLane.getLeadingVehicle(this);
	}

	@Override
	public final Vehicle precedingVehicle() {
		return currentLane.getPrecedingVehicle(this);
	}

	@Override
	public void onRefresh() {
		currentLane.update(this);
	}

	/*
	 * Determine the order of the vehicles in the NavigableSet of the lane
	 * Vehicle Queue
	 */
	@Override
	public int compareTo(DefaultVehicle vehicle) {
		return position - vehicle.position() > 0 ? 1 : position
				- vehicle.position() < 0 ? -1 : 0;
	}

	@Override
	public String toString() {
		return getName();
	}

	// TODO move to type
	@Override
	public final double getWidth() {
		return width;
	}

	@Override
	public final double getLength() {
		return length;
	}

	public final void setWidth(double width) {
		this.width = width;
	}

	public final void getLength(double length) {
		this.length = length;
	}

	@Override
	public ConnectionLane preferredConnector() {
		return preferredConnector;
	}

	@Override
	public void preferredConnector(ConnectionLane lane) {
		preferredConnector = lane;
	}

	@Override
	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	@Override
	public double getMaxSpeed() {
		return maxSpeed;
	}

	@Override
	public double getDesiredHeadway() {
		return desiredHeadway;
	}

	@Override
	public double getReactionTime() {
		return reactionTime;
	}
}
