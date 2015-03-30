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
package org.tripsim.api.model;

import org.tripsim.api.Environment;

import com.vividsolutions.jts.geom.Coordinate;

public interface Positionable {

	// TODO get history motion
	Motion getMotion();

	double getWidth();

	double getLength();

	Environment getCurrentEnvironment();

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
