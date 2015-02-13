package edu.trafficsim.engine.statistics;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Vehicle;

class VehicleState {

	final double time;

	final long vid;
	final Coordinate coord;
	final double speed;
	final double angle;

	VehicleState(double time, Vehicle vehicle) {
		this.time = time;
		vid = vehicle.getId();
		coord = vehicle.coord();
		speed = vehicle.speed();
		angle = vehicle.angle();
	}
}
