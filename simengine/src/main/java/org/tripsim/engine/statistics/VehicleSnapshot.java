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
