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
package org.tripsim.model.core;

import java.io.Serializable;

import org.tripsim.api.model.Motion;

public class DefaultMotion implements Motion, Serializable {
	private static final long serialVersionUID = 1L;

	double position = -1;
	double lateralOffset = 0;
	double direction = 0;

	double speed = 0;
	double acceleration = 0;

	public DefaultMotion() {
	}

	public DefaultMotion(Motion motion) {
		this.position = motion.getPosition();
		this.lateralOffset = motion.getLateralOffset();
		this.direction = motion.getDirection();
		this.speed = motion.getSpeed();
		this.acceleration = motion.getAcceleration();
	}

	public DefaultMotion(double position, double speed) {
		this.position = position;
		this.speed = speed;
	}

	@Override
	public final double getPosition() {
		return position;
	}

	@Override
	public final void setPosition(double position) {
		this.position = position;
	}

	@Override
	public double getLateralOffset() {
		return lateralOffset;
	}

	@Override
	public void setLateralOffset(double lateralOffset) {
		this.lateralOffset = lateralOffset;
	}

	@Override
	public double getDirection() {
		return direction;
	}

	@Override
	public void setDirection(double direction) {
		this.direction = direction;
	}

	@Override
	public final double getSpeed() {
		return speed;
	}

	@Override
	public final void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public final double getAcceleration() {
		return acceleration;
	}

	@Override
	public final void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public void update(Motion motion) {
		this.position = motion.getPosition();
		this.lateralOffset = motion.getLateralOffset();
		this.direction = motion.getDirection();
		this.speed = motion.getSpeed();
		this.acceleration = motion.getAcceleration();
	}

	@Override
	public String toString() {
		return "DefaultMotion [position=" + position + ", lateralOffset="
				+ lateralOffset + ", direction=" + direction + ", speed="
				+ speed + ", acceleration=" + acceleration + "]";
	}

}
