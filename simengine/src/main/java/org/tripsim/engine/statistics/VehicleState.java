package org.tripsim.engine.statistics;

import java.io.Serializable;

public class VehicleState implements Serializable {

	private static final long serialVersionUID = 1L;

	long sequence;

	long vid;
	double lat; // y
	double lon; // x
	double position;
	double speed;
	double accel;
	double angle;

	public long getSequence() {
		return sequence;
	}

	void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getVid() {
		return vid;
	}

	void setVid(long vid) {
		this.vid = vid;
	}

	public double getLon() {
		return lon;
	}

	void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	void setLat(double lat) {
		this.lat = lat;
	}

	public double getPosition() {
		return position;
	}

	void setPosition(double position) {
		this.position = position;
	}

	public double getSpeed() {
		return speed;
	}

	void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAccel() {
		return accel;
	}

	void setAccel(double accel) {
		this.accel = accel;
	}

	public double getAngle() {
		return angle;
	}

	void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public String toString() {
		return "VehicleState [sequence=" + sequence + ", vid=" + vid + ", lon="
				+ lon + ", lat=" + lat + ", position=" + position + ", speed="
				+ speed + ", accel=" + accel + ", angle=" + angle + "]";
	}

}
