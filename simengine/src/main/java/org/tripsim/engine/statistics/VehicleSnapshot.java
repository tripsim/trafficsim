package org.tripsim.engine.statistics;

import org.tripsim.api.model.Link;
import org.tripsim.api.model.Vehicle;

import com.vividsolutions.jts.geom.Coordinate;

class VehicleSnapshot {

	final Long vid;
	final Coordinate coord;
	final double position;
	final double speed;
	final double accel;
	final double angle;

	final Long linkId;
	final Long nodeId;

	public VehicleSnapshot(Vehicle vehicle) {
		vid = vehicle.getId();
		coord = vehicle.getCoord();
		position = vehicle.getPosition();
		speed = vehicle.getSpeed();
		accel = vehicle.getAcceleration();
		angle = vehicle.getAngle();

		Link link = vehicle.getCurrentLink();
		linkId = link.getId();
		if (position < link.getStartNode().getRadius()) {
			nodeId = link.getStartNode().getId();
		} else if (position > link.getLength() - link.getEndNode().getRadius()) {
			nodeId = link.getEndNode().getId();
		} else {
			nodeId = null;
		}
	}

}
