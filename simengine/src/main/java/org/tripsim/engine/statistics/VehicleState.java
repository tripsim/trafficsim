/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
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
