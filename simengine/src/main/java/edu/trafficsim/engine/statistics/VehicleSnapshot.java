package edu.trafficsim.engine.statistics;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Vehicle;

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
		coord = vehicle.coord();
		position = vehicle.position();
		speed = vehicle.speed();
		accel = vehicle.acceleration();
		angle = vehicle.angle();

		linkId = vehicle.getLink() == null ? null : vehicle.getLink().getId();
		nodeId = vehicle.getNode() == null ? null : vehicle.getNode().getId();
	}

}
