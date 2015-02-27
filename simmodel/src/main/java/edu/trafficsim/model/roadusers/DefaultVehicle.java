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
import edu.trafficsim.model.CrusingType;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Subsegment;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.VehicleClass;
import edu.trafficsim.model.core.MovingObject;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultVehicle extends MovingObject<DefaultVehicle> implements
		Vehicle {

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

	private Node origin = null;
	private Node destination = null;
	private Link targetLink = null;
	private ConnectionLane preferredConnector = null;
	private Lane currentLane = null;

	public DefaultVehicle(long id, String name, long startFrame,
			VehicleClass vehicleClass, String vehicleType, String driverType) {
		super(id, name, startFrame);
		this.vehicleClass = vehicleClass;
		this.vehicleType = vehicleType;
		this.driverType = driverType;
	}

	// Vehicle Properties
	@Override
	public final String getVehicleType() {
		return vehicleType;
	}

	public final void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public final String getDriverType() {
		return driverType;
	}

	public final void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public CrusingType getCrusingType() {
		return crusingType;
	}

	public void setCrusingType(CrusingType crusingType) {
		this.crusingType = crusingType;
	}

	@Override
	public final double getWidth() {
		return width;
	}

	public final void setWidth(double width) {
		this.width = width;
	}

	@Override
	public final double getLength() {
		return length;
	}

	public final void setLength(double length) {
		this.length = length;
	}

	@Override
	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	public void setDesiredSpeed(double desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

	@Override
	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public double getDesiredHeadway() {
		return desiredHeadway;
	}

	public void setDesiredHeadway(double desiredHeadway) {
		this.desiredHeadway = desiredHeadway;
	}

	@Override
	public double getPerceptionTime() {
		return perceptionTime;
	}

	public void setPerceptionTime(double perceptionTime) {
		this.perceptionTime = perceptionTime;
	}

	@Override
	public double getReactionTime() {
		return reactionTime;
	}

	public void setReactionTime(double reactionTime) {
		this.reactionTime = reactionTime;
	}

	// Vehicle States

	@Override
	public Segment getSegment() {
		return currentLane != null ? currentLane.getSegment() : null;
	}

	@Override
	public Subsegment getSubsegment() {
		return currentLane;
	}

	@Override
	public final Node getNode() {
		return onConnector() ? ((ConnectionLane) currentLane).getNode() : null;
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
	public ConnectionLane preferredConnector() {
		return preferredConnector;
	}

	@Override
	public void preferredConnector(ConnectionLane lane) {
		preferredConnector = lane;
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
	public Node origin() {
		return origin;
	}

	void origin(Node origin) {
		this.origin = origin;
	}

	@Override
	public final Node destination() {
		return destination;
	}

	void destination(Node destination) {
		this.destination = destination;
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

}
