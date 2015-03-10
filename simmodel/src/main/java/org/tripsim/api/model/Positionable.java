package org.tripsim.api.model;

import com.vividsolutions.jts.geom.Coordinate;

public interface Positionable {

	// TODO get history motion
	Motion getMotion();

	double getWidth();

	double getLength();

	/**
	 * 
	 * position relative to the start of node, it should ignore offsets of path
	 * (lane / link)
	 * 
	 * 
	 * @return
	 */
	double getPosition();

	double getLateralOffset();

	double getDirection();

	double getSpeed();

	double getAcceleration();

	Coordinate getCoord();

	double getAngle();

	boolean isAheadOf(Movable movable);
}
